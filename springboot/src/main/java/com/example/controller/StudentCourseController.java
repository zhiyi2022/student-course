package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.entity.StudentCourse;
import com.example.service.StudentCourseService;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/25 11:20
 */
@RestController
@RequestMapping("/studentCourse")
public class StudentCourseController {

    @Resource
    StudentCourseService studentCourseService;

    /**
     * 学生选课
     */
    @PostMapping("/add")
    public Result add (@RequestBody StudentCourse studentCourse) {
        studentCourseService.add(studentCourse);
        return Result.success();
    }

    /**
     * 删除选课
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        studentCourseService.deleteById(id);
        return Result.success();
    }

    /**
     * 分页条件查询课程
     */
    @GetMapping("/selectPage")       //分页查询接口
    public Result selectPage (@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              StudentCourse studentCourse) {
        PageInfo<StudentCourse> pageInfo = studentCourseService.selectPage(pageNum, pageSize, studentCourse);
        return Result.success(pageInfo);     //返回分页数据
    }
}
