package com.example.entity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/26 0:12
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Load {
    private static int[] weights; // 物品的重量数组
    private static int capacity; // 载体的容量
    private static int maxWeight; // 最大总重量
    private static List<Integer> currentSolution; // 当前解
    private static List<Integer> bestSolution; // 最优解

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入物品重量数组
        System.out.print("请输入物品的权重(用空格分隔): ");
        String[] weightsStr = scanner.nextLine().split(" ");
        weights = Arrays.stream(weightsStr).mapToInt(Integer::parseInt).toArray();

        // 输入载体容量
        System.out.print("请输入装载的容量: ");
        capacity = scanner.nextInt();

        maxWeight = Integer.MIN_VALUE;
        currentSolution = new ArrayList<>();
        bestSolution = new ArrayList<>();

        backtracking(0, 0, 0);

        // 输出最优解
        System.out.println("Best solution: " + bestSolution);
        System.out.println("Maximum total weight: " + maxWeight);
    }

    private static void backtracking(int index, int currentWeight, int currentTotalWeight) {
        if (index == weights.length) {
            // 已经考虑完所有物品
            if (currentTotalWeight > maxWeight && currentWeight <= capacity) {
                // 更新最优解
                maxWeight = currentTotalWeight;
                bestSolution = new ArrayList<>(currentSolution);
            }
            return;
        }

        // 考虑装载当前物品
        currentSolution.add(weights[index]);
        backtracking(index + 1, currentWeight + weights[index], currentTotalWeight + weights[index]);
        currentSolution.remove(currentSolution.size() - 1); // 回溯

        // 考虑不装载当前物品
        backtracking(index + 1, currentWeight, currentTotalWeight);
    }
}

