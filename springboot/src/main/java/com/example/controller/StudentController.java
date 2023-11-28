package com.example.controller;

import com.example.common.Result;
import com.example.entity.Student;
import com.example.service.StudentService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/20 20:02
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Resource
    StudentService studentService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add (@RequestBody Student student) {
        studentService.add(student);
        return Result.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete (@PathVariable Integer id) {
        studentService.deleteById(id);
        return Result.success();
    }

    /**
     * 更新
     */
    @PutMapping ("/update")
    public Result update (@RequestBody Student student) {
        studentService.updateById(student);
        return Result.success();
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage (@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              Student student) {
        PageInfo<Student> pageInfo = studentService.selectPage(pageNum, pageSize, student);   //这一行查询全部一定要写在中间
        return Result.success(pageInfo);
    }
}
