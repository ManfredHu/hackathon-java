package com.hackathon.api;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hackathon.model.Cart;
import com.hackathon.model.Food;
import com.hackathon.param.FoodParam;
import com.hackathon.result.CartsResult;
import com.hackathon.result.ErrorResult;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;

import java.io.IOException;

/**
 * Created by beatk on 2015/11/29.
 */
public class CartsApi extends Servlet {

    @Override
    public void doGet(Request request, Response response) {

    }

    @Override
    public void doPost(Request request, Response response) {

        //新建一个购物车，并添加到数据库（内存数据库）
        Cart cart = null;
        try {
            cart = new Cart(Integer.parseInt(
                    request.getSession(false).getArtribute("userId").toString()));
        }catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode("500","ERROR");
            ErrorResult er = new ErrorResult();
            er.setCode("SERVER_ERROR");
            er.setMessage("服务器错误");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        cartDao.addCart(cart);

        //创建返回的结果对象
        CartsResult cr = new CartsResult();
        cr.setCart_id(cart.getId());

        //响应输出
        try {
            response.outPut(cr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPatch(Request request, Response response) {

        Gson gson = new Gson();
        FoodParam fd;

        try {
            fd = gson.fromJson(request.getData(),FoodParam.class);
        }catch (JsonSyntaxException e) {

            e.printStackTrace();

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

        Cart cart = cartDao.getCart(request.getPatchParam());
        if(cart == null) {

            //篮子找不到
            response.setStatusCode("404","Not Found");
            ErrorResult er = new ErrorResult();
            er.setCode("CART_NOT_FOUND");
            er.setMessage("篮子不存在");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        Session session = request.getSession(false);
        Integer userId = Integer.parseInt(
                session.getArtribute("userId").toString());
        if(!cart.getUserId().equals(userId)) {

            //篮子不属于该用户
            response.setStatusCode("401","Unauthorized");
            ErrorResult er = new ErrorResult();
            er.setCode("NOT_AUTHORIZED_TO_ACCESS_CART");
            er.setMessage("无权限访问指定的篮子");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        //查找食物，是否还有库存
        Food food = foodDao.getFood(fd.getFood_id());
        if(food == null) {

            //食物找不到
            response.setStatusCode("404","Not Found");
            ErrorResult er = new ErrorResult();
            er.setCode("FOOD_NOT_FOUND");
            er.setMessage("食物不存在");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        if((food.getStock() - fd.getCount()) < 0) {

            //食物找不到
            response.setStatusCode("405","Not Enough");
            ErrorResult er = new ErrorResult();
            er.setCode("FOOD_NOT_Enough");
            er.setMessage("食物不足");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }


        Integer foodid = fd.getFood_id();
        Integer foodCount = fd.getCount();
        Integer foodPrice = food.getPrice();

        //添加食物
        boolean isSuccess = cart.addItems(foodid,foodCount,foodPrice);

        if(!isSuccess) {

            //食物找不到
            response.setStatusCode("403","Forbidden");
            ErrorResult er = new ErrorResult();
            er.setCode("FOOD_OUT_OF_LIMIT");
            er.setMessage("篮子中食物数量超过了三个");
            try {
                response.outPut(er);
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            return;
        }

        //成功添加
        response.setStatusCode("204","No content");
        try {
            response.outPut();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }
}
