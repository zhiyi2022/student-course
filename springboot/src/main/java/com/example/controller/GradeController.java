package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.entity.Grade;
import com.example.service.GradeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/26 21:16
 */
@RestController
@RequestMapping("/grade")
public class GradeController {

    @Resource
    GradeService gradeService;


    @PostMapping("/add")
    public Result add (@RequestBody Grade grade) {
        gradeService.add(grade);
        return Result.success();
    }

    @PutMapping("/update")
    public Result update (@RequestBody Grade grade) {
        gradeService.update(grade);
        return Result.success();
    }

    // 分页条件查询课程
    @GetMapping("/selectPage")       //分页查询接口
    public Result selectPage (@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              Grade grade) {
        PageInfo<Grade> pageInfo = gradeService.selectPage(pageNum, pageSize, grade);
        return Result.success(pageInfo);     //返回分页数据
    }
}
