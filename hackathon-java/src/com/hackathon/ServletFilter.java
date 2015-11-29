package com.hackathon;

import com.hackathon.result.ErrorResult;
import com.hackathon.servlet.Filter;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;

import java.io.IOException;

/**
 * Created by beatk on 2015/11/27.
 */
public class ServletFilter extends Filter {

    private static String[] noFilterURIs = {"/","/login"};


    public ServletFilter(Servlet servlet) {
        super(servlet);
    }

    @Override
    public void doFilter(Request request, Response response) {

        //属于不过滤列表中的URI，直接通过过滤
        for(String noFilterURI : noFilterURIs) {
            if(noFilterURI.equals(request.getRequestURI())) {
                doPass(request,response);
                return;
            }
        }

        //如果可以获得有效的Session，通过过滤
        if(request.getSession(false) != null) {
            doPass(request,response);
            return;
        }

        //无法获得有效的Session,返回错误
        ErrorResult er = new ErrorResult();
        er.setCode("INVALID_ACCESS_TOKEN");
        er.setMessage("无效的令牌");

        //设置状态码
        response.setStatusCode("401","Unauthorized");

        //输出响应并结束
        try {
            response.outPut(er);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
