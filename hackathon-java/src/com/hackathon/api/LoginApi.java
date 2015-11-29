package com.hackathon.api;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hackathon.model.User;
import com.hackathon.param.LoginParam;
import com.hackathon.result.ErrorResult;
import com.hackathon.result.LoginResult;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;

import java.io.IOException;

/**
 * Created by beatk on 2015/11/28.
 * 匹配路径：/login
 */
public class LoginApi extends Servlet {

    @Override
    public void doGet(Request request, Response response) {

    }

    @Override
    public void doPost(Request request, Response response)  {

        Gson gson = new Gson();

        LoginParam lp = null;

        try {

            lp = gson.fromJson(request.getData(),LoginParam.class);
            System.out.println("**** "+ lp.getUsername() + " " + lp.getPassword());

        }catch (JsonSyntaxException e) {

            //json格式错误，返回指定信息
            response.setStatusCode("400","Bad Request");
            ErrorResult er = new ErrorResult();
            er.setCode("MALFORMED_JSON");
            er.setMessage("格式错误");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        User user = userDao.getUser(lp.getUsername());

        //用户名不存在
        if(user == null) {
            response.setStatusCode("403","Bad Request");
            ErrorResult er = new ErrorResult();
            er.setCode("USER_AUTH_FAIL");
            er.setMessage("用户名或密码错误");
            try {
                response.outPut(er);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        //密码错误
        if(!user.getPassword().equals(lp.getPassword())) {
            response.setStatusCode("403","Bad Request");
            ErrorResult er = new ErrorResult();
            er.setCode("USER_AUTH_FAIL");
            er.setMessage("用户名或密码错误");
            try {
                response.outPut(er);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        //设置Session
        Session session = request.getSession();
        session.setAttribute("userId",user.getId());

        LoginResult lr = new LoginResult();
        lr.setUser_id(user.getId());
        lr.setUsername(user.getName());
        lr.setAccess_token(session.getAcessToken());

        //返回成功的Json信息
        try {
            response.setStatusCode("200","OK");
            response.outPut(lr);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void doPatch(Request request, Response response) {

    }
}
