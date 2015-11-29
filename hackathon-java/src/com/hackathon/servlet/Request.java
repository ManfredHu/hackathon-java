package com.hackathon.servlet;

import java.util.Map;

/**
 * Created by beatk on 2015/11/27.
 */
public interface Request {

    /**
     * 获取请求报头
     * @param name  报头项名称
     * @return      报头项的值
     */
    String getHeader(String name);

    /**
     * 获取请求方法的类型
     * @return      请求方法类型
     */
    String getRequestType();

    /**
     * 获取请求的URI
     * @return  返回请求URI
     */
    String getRequestURI();

    /**
     * 获取Get参数值
     * @param name  参数名
     * @return      参数值
     */
    String getParameter(String name);

    /**
     * 获取或创建Session，如果用户第一次访问，
     * 则创建session，否则返回用户Session，等价于调用getSession(true)
     * @return  Session对象
     */
    Session getSession();

    /**
     * 获取Session，指定是否创建新的Session
     * @param isCreated     指定是否创建新的Session
     * @return  Session对象
     */
    Session getSession(boolean isCreated);

    /**
     * 获取请求体内容（一般为Json数据）
     * @return  请求体内容
     */
    String getData();
}
