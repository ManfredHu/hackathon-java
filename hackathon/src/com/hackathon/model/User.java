package com.hackathon.model;

/**
 * Created by beatk on 2015/11/28.
 */
public class User {

    private Integer id;             //用户id
    private String name;            //用户名
    private String password;        //用户密码

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
