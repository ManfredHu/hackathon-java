package com.hackathon.servlet.impl;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by beatk on 2015/11/27.
 */
public class HeaderMap<K,V> extends HashMap<K,V> {

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("");

        for (Map.Entry e : this.entrySet()) {
            sb.append(e.getKey() + ": " + e.getValue() + "\r\n");
        }

        return sb.toString();
    }
}
