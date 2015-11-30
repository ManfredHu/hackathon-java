package com.hackathon.api;

import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;

import java.io.IOException;

/**
 * Created by beatk on 2015/11/28.
 * 匹配路径：/
 */
public class Welcome extends Servlet {

    @Override
    public void doGet(Request request, Response response) {
        try {
            response.setHeader("Content-Type", "text/html; charset=utf-8");
            response.outPut("欢迎光临");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(Request request, Response response) {

    }

    @Override
    public void doPatch(Request request, Response response) {

    }
}
