package com.hackathon.api;

import com.hackathon.model.Cart;
import com.hackathon.result.CartsResult;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;

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
        Cart cart = new Cart();
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

    }
}
