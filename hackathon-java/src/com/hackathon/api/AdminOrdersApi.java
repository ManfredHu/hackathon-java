package com.hackathon.api;

import java.io.IOException;
import java.util.List;

import com.hackathon.model.Order;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;

/**
 * Created by beatk on 2015/11/29.
 */
public class AdminOrdersApi extends Servlet {

    @Override
    public void doGet(Request request, Response response) {
		//检测是否是后台登陆
		
		System.out.println("123456");
		
		//获得所有订单的实体
		List<Order> listOrders = orderDao.getAllOrders();
		System.out.println("1234568"+listOrders);
		
		//输出所有订单
		try {
			response.outPut(listOrders);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
