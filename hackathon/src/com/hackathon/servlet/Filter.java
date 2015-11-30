package com.hackathon.servlet;

import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;

/**
 * Created by beatk on 2015/11/27.
 */
public abstract class Filter {

    private Servlet servlet;

    public Filter(Servlet servlet) {
        this.servlet = servlet;
    }

    /**
     * 执行过滤
     * @param request       request对象
     * @param response      response对象
     */
    public abstract void doFilter(Request request,Response response);

    /**
     * 通过过滤，回调指定Servlet
     * @param request       request对象
     * @param response      response对象
     */
    protected void doPass(Request request,Response response) {

        if(request.getRequestType().equals("GET"))
            this.servlet.doGet(request,response);
        else if(request.getRequestType().equals("POST"))
            this.servlet.doPost(request,response);
        else
            this.servlet.doPatch(request,response);
    }
}
