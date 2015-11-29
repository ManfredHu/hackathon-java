package com.hackathon.servlet.impl;

import com.hackathon.servlet.Session;
import org.hibernate.id.GUIDGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beatk on 2015/11/27.
 */
public class ServletSession implements Session {

    private static Map<String,Session> sessionPool = new HashMap<String,Session>();

    public String access_token = "";
    private Map<String,Object> attributes = new HashMap<String,Object>();

    public ServletSession() {
        this.access_token = this.hashCode() + "";
        this.sessionPool.put(access_token, this);

    }

    @Override
    public Object setAttribute(String name, Object value) {
        return this.attributes.put(name,value);
    }

    @Override
    public Object getArtribute(String name) {
        return this.attributes.get(name);
    }

    @Override
    public String getAcessToken() {
        return this.access_token;
    }

    /**
     * 在session池中根据给出的access_token查找指定session
     * @param access_token  id号
     * @return              如果存在，返回指定session，不存在则返回null
     */
    public static Session getSession(String access_token) {
        return sessionPool.get(access_token);
    }
}
