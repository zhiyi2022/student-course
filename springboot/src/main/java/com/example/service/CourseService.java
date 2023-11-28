package com.example.service;

import com.example.entity.Course;
import com.example.mapper.CourseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/19 16:03
 */
@Service
public class CourseService {

    @Resource
    private CourseMapper courseMapper;

    // total 是查询的总数   list是数据列表
    // pageNum 是当前的页码   pageSize是当前页的个数
    @GetMapping("/selectPage")
    public PageInfo<Course> selectPage (Integer pageNum, Integer pageSize, Course course) {
        PageHelper.startPage(pageNum,pageSize);
        List<Course> courseList = courseMapper.selectAll(course);
        return PageInfo.of(courseList);
    }

    // 新增数据
    public void add(Course course) {
        courseMapper.insert(course);
    }

    // 修改数据
    public void updateById(Course course) {
        courseMapper.updateById(course);
    }

    public void deleteById(Integer id) {
        courseMapper.deleteById(id);
    }
}
