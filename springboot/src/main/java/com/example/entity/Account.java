package com.example.entity;

/**
 * 功能：
 * 作者：淏哥
 * 日期：2023/11/20 20:03
 */
public class Account {

    private String username;
    private String password;
    private String name = "";
    private String role;



    public Account() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

