package com.hackathon.result;

/**
 * Created by beatk on 2015/11/28.
 */
public class ErrorResult {

    private String code = "";       //错误代码
    private String message = "";    //错误信息

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
