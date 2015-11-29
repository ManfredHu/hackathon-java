package com.hackathon.servlet;

import java.io.IOException;

/**
 * Created by beatk on 2015/11/27.
 */
public interface Response {

    /**
     * 设置响应报头
     * @param name  报头名
     * @param value 报头值
     * @return      如果是一个新的报头，则返回null，否则返回原有报头的值
     */
    String setHeader(String name,String value);

    /**
     * 输出响应数据
     * @param outputData    响应的数据（可以转化为Json的对象）
     * @throws IOException  IO异常
     */
    void outPut(Object outputData) throws IOException;

    /**
     * 无响应内容的输出
     * @throws IOException  IO异常
     */
    void outPut() throws IOException;

    /**
     * 设置响应状态码
     * @param code      状态码
     * @param statement 状态码说明
     */
    void setStatusCode(String code,String statement);

}
