package com.hackathon.result;

/**
 * Created by beatk on 2015/11/28.
 */
public class LoginResult {

    private Integer user_id = 0;
    private String username = "";
    private String access_token = "";

    public Integer getUser_id() {
        return user_id;
    }

    public String getUsername() {
        return username;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}
