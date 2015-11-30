package com.hackathon.api;

import java.io.IOException;
import java.util.List;

import com.hackathon.model.Order;
import com.hackathon.model.User;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;

/**
 * Created by beatk on 2015/11/29.
 */
public class AdminOrdersApi extends Servlet {

    @Override
    public void doGet(Request request, Response response) {

		//检测是否是后台登陆
        Session session = request.getSession(false);
        Integer userId = (Integer)session.getArtribute("userId");
		User user = userDao.getUser(userId);
        if(!user.getName().equals("root")) {
            try {
                response.outPut();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                return;

            }
        }

		//获得所有订单的实体
		List<Order> listOrders = orderDao.getAllOrders();

		//输出所有订单
		try {
			response.outPut(listOrders);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Override
    public void doPost(Request request, Response response) {

    }

    @Override
    public void doPatch(Request request, Response response) {

    }
}
