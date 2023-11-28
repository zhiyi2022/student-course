package com.example.controller;

import com.example.common.Result;
import com.example.entity.Course;
import com.example.service.CourseService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/19 16:00
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Resource
    private CourseService courseService;


    // 分页查询
    @GetMapping("/selectPage")       //分页查询接口
    public Result selectPage (@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "5") Integer pageSize,
                              Course course) {
        PageInfo<Course> pageInfo = courseService.selectPage(pageNum, pageSize, course);
        return Result.success(pageInfo);     //返回分页数据
    }

    /**
     * 添加课程
     */
    @PostMapping("/add")
    public Result add (@RequestBody Course course) {    //@RequestBody 作用是把前端的JSON数据传过来
        courseService.add(course);
        return Result.success();
    }

    /**
     * 更新课程信息
     */
    @PutMapping ("/update")
    public Result update (@RequestBody Course course) {    //@RequestBody 作用是把前端的JSON数据传过来
        courseService.updateById(course);
        return Result.success();
    }

    /**
     *删除课程
     */
    @DeleteMapping ("/delete/{id}")
    public Result delete (@PathVariable Integer id) {    //@RequestBody 作用是把前端的JSON数据传过来
        courseService.deleteById(id);
        return Result.success();
    }
}
