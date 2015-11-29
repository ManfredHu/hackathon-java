package com.hackathon.servlet.impl;

import com.hackathon.servlet.Request;
import com.hackathon.servlet.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beatk on 2015/11/27.
 */
public class ServletRequest implements Request {

    private Map<String,String> header = new HashMap<String,String>();

    private String requestType = "";

    private String requestURI = "";

    private Map<String,String> param = new HashMap<String,String>();

    private String requestBody = "";

    private Session session = null;

    public ServletRequest(String requestHeader,String requestBody) {

        //1: 解析请求报头
        String[] tmp = requestHeader.split("\n");
        String[] tmp2 = tmp[0].split(" ");

        this.requestType = tmp2[0].toUpperCase(); //默认大写表示
        this.requestURI = tmp2[1];
        for(int i = 1; i < tmp.length; i++) {
            String[] tmp3 = tmp[i].split(": ");
            this.header.put(tmp3[0],tmp3[1]);
        }

        //2: 解析URI
        tmp = this.requestURI.split("\\?");
        this.requestURI = tmp[0];

        if(tmp.length >= 2) {

            tmp2 = tmp[1].split("&");
            for(int i = 1; i < tmp.length; i++) {
                String[] tmp3 = tmp2[i].split("=");
                this.param.put(tmp3[0],tmp3[1]);
            }
        }


        //3: 解析请求体
        this.requestBody = requestBody;

    }


    @Override
    public String getHeader(String name) {
        return this.header.get(name);
    }

    @Override
    public String getRequestType() {
        return this.requestType;
    }

    @Override
    public String getRequestURI() {
        return this.requestURI;
    }

    @Override
    public String getParameter(String name) {
        return this.param.get(name);
    }

    @Override
    public Session getSession() {

        return getSession(true);
    }

    @Override
    public Session getSession(boolean isCreated) {

        if(this.getParameter("access_token") != null
                || this.getHeader("Access-Token") != null) {

            String access_token = "";
            if((access_token = this.getParameter("access_token")) != null);
            else {
                access_token = this.getHeader("Access-Token");
                System.out.println("*****" + this.header); 
            }

            //如果access_token格式有问题，返回null
            try {
                return ServletSession.getSession(access_token);
            }catch (NumberFormatException e) {
                return null;
            }
        }
        else if(isCreated) {  //不存在access_token则新建一个Session
            return new ServletSession();
        }
        else return null;

    }

    @Override
    public String getData() {
        return this.requestBody;
    }
}
