package com.hackathon.servlet.impl;

import com.google.gson.Gson;
import com.hackathon.servlet.Response;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Created by beatk on 2015/11/27.
 */
public class ServletResponse implements Response {

    private Socket socket;
    private OutputStream out;

    private Map<String,String> header = new HeaderMap<String,String>();

    private String statusCode = "200";

    private String codeStatement = "OK";

    public ServletResponse(Socket socket) {

        this.socket = socket;
        try {
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //设置默认header
        header.put("Content-Type","application/json; charset=utf8");
        header.put("Content-Length","0");

    }


    @Override
    public String setHeader(String name, String value) {
        return header.put(name,value);
    }

    @Override
    public void setStatusCode(String code, String statement) {
        this.statusCode = code;
        this.codeStatement = statement;
    }

    @Override
    public void outPut(Object outputData) throws IOException {

        Gson gson = new Gson();

        //将数据转化为Json
        String outputString = gson.toJson(outputData);

        //设置响应体大小（响应的信息的字节数）
        setHeader("Content-Length", outputString.getBytes("UTF-8").length + "");


        System.out.println("***Response: \n" +
                "HTTP/1.1 " + this.statusCode + " " + this.codeStatement + "\r\n"
                + this.header + "\r\n" + outputString);

        out.write(("HTTP/1.1 " + this.statusCode + " " + this.codeStatement + "\r\n"
                + this.header + "\r\n" + outputString).getBytes("UTF-8"));
        out.flush();
    }

    @Override
    public void outPut() throws IOException {

        OutputStream os =  socket.getOutputStream();

        System.out.println("***Response: \n" +
                this.statusCode + " " + this.codeStatement + "\n"
                + this.header);

        out.write((this.statusCode + " " + this.codeStatement + "\n"
                + this.header ).getBytes("UTF-8"));


        os.flush();
    }
}
