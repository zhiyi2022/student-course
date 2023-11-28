package com.example.service;

import com.example.entity.Account;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/17 23:14
 */
@Service
public class AdminService {

    @Resource
    private AdminMapper adminMapper;
    /**
     * 登录
     */
    public Account login (Account account) {
        Account dbAdmin = adminMapper.selectByUsername(account.getUsername());
        if (dbAdmin == null) {
            // 没有用户
            throw new CustomException("账号或密码错误");
        }
        // 比较密码
        if (!account.getPassword().equals(dbAdmin.getPassword())) {
            throw new CustomException("账号或密码错误");
        }
        // 登陆成功
        return dbAdmin;
    }
}
