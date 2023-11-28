package com.example.entity;


public class FlowShop {
    int n;//作业数
    int f1;//机器1完成处理时间
    int f;//完成时间和
    int bestf;//当前最优值
    int[][] m;//各作业所需的处理时间
    int []x;//当前作业调度
    int[] bestx;//当前最优作业调度
    int[] f2;//机器2完成处理时间

    //初始化数据成员
    public void bestFlow(int n,int[][] m) {
        this.n=n;
        this.m=m;
        f1=0;
        f=0;
        bestf=10000;//给定初始值
        bestx=new int[n+1];
        x=new int[n+1];

        //初始化，x[i]为原始排序
        for(int i=1;i<=n;i++){
            x[i]=i;
        }
        f2=new int[n+1];

        //调用backtrack函数，求解最优调度方案
        backtrack(1);

    }
    //swap函数实现更改作业调度顺序
    public  void swap(int[] x,int i,int j){
        int temp=x[i];
        x[i]=x[j];
        x[j]=temp;
    }

    //回溯法实现批处理调度问题
    public  void backtrack(int i){
        //搜索第i个结点
        if(i>n){ //i>n说明已到达叶结点
            for(int j=1;j<=n;j++) {
                bestx[j]=x[j];//获取当前的最优调度方案
            }
            bestf=f;//获取当前的最优值
        }
        else{

            for(int j=i;j<=n;j++){//调度每个作业
                //作业x[j]在第一台机器的时间
                f1+=m[x[j]][1];

                //f2[i]等于f2[i-1]和f1中较大者加上作业x[j]在第2台机器的时间
                f2[i]=((f2[i-1]>f1)?f2[i-1]:f1)+m[x[j]][2];
                f+=f2[i];

                if(f<bestf){//如果搜索结果小于当前最优解，则继续向下搜索
                    swap(x,i,j);
                    backtrack(i+1);
                    swap(x,i,j);
                }
                //往回走时，还原数值
                f1-=m[x[j]][1];
                f-=f2[i];
            }
        }
    }
    public static void main(String[] args) {
        int n=3;
        //m的下标从1开始，因此第一行的0和每一行第一列的0，仅为了方便开始顺序为1
        int[][] m={{0,0,0},{0,2,1},{0,3,1},{0,2,3}};

        FlowShop f=new FlowShop();

        f.bestFlow(n, m);
        System.out.println("最优批处理作业调度顺序为：");
        for(int i=1;i<=n;i++) {
            System.out.print(f.bestx[i]+" ");
        }
        System.out.println();
        System.out.println("最优调度所需的最短时间为："+f.bestf);
    }
}
