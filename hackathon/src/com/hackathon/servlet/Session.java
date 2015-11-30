package com.hackathon.servlet;

/**
 * Created by beatk on 2015/11/27.
 */
public interface Session {

    /**
     * 设置属性
     * @param name   属性名
     * @param value  属性值
     * @return
     */
    Object setAttribute(String name,Object value);

    /**
     * 获取属性
     * @param name 属性名
     * @return
     */
    Object getArtribute(String name);

    /**
     * 获取access_token(也就是SSID)
     * @return SSID
     */
    String getAcessToken();
}
