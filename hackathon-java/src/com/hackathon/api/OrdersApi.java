package com.hackathon.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.expression.spel.ast.OpAnd;

import com.google.gson.Gson;
import com.hackathon.dao.CartDao;
import com.hackathon.model.Cart;
import com.hackathon.model.Order;
import com.hackathon.model.Cart.Item;
import com.hackathon.param.OrderParam;
import com.hackathon.result.ErrorResult;
import com.hackathon.result.OrderResult;
import com.hackathon.result.OrdersResult;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;


/**
 * Created by beatk on 2015/11/29.
 */
public class OrdersApi extends Servlet {
	
	private static Object lock = new Object();

    @Override
    public void doGet(Request request, Response response) {
    	
    	Session session =request.getSession();
    	int sum =0;
    	//查询订单
    	OrdersResult or = new  OrdersResult();
    	Order order = orderDao.getUserOrder(Integer.parseInt(session.getArtribute("userId").toString()));
    	if(order!=null){
	    	or.setId(order.getId());
	    	for(Item item : order.getItems()){
	    		or.addItem(item.getFoodID(),item.getCount());
	    		sum +=item.getPrice()*item.getCount();
	    	}
	    	or.setTotal(sum);
	    	
	    	try {
	        	response.outPut(or);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("查询订单出错！");
			}
    	}
    	else{
    		//response.setStatusCode("204", "No content");
    		//response.setHeader("Content-Type", "application/text; charset=utf8");
    		try {
				response.outPut("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    }

    @Override
    public void doPost(Request request, Response response) {
    		
    	String data = request.getData();
    	Gson gson = new Gson();
    	OrderParam op = new OrderParam();
    	
    	//分解所传递的数据
    	op = gson.fromJson(data, OrderParam.class);
    	
    	Cart cart = cartDao.getCart(op.getCart_id());
    	
    	//篮子不存在时，返回404错误
    	if(cart ==null){
    		response.setStatusCode("404","Not Found");
            ErrorResult er = new ErrorResult();
            er.setCode("CART_NOT_FOUND");
            er.setMessage("篮子不存在");
            try {
                response.outPut(er);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
    	}
 
    	//篮子不属于当前用户
    	Session session = request.getSession();
    	if(!session.getArtribute("userId").equals(cart.getUserId())){
    		response.setStatusCode("403","Forbidden");
            ErrorResult er = new ErrorResult();
            er.setCode("NOT_AUTHORIZED_TO_ACCESS_CART");
            er.setMessage("无权限访问指定的篮子");
            try {
                response.outPut(er);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
    	}
    	
    	//超过下单次数限制
    	if(orderDao.getUserOrder(Integer.parseInt(session.getArtribute("userId").toString())) != null) {
    				response.setStatusCode("403","Forbidden");
	                ErrorResult er = new ErrorResult();
	                er.setCode("ORDER_OUT_OF_LIMIT");
	                er.setMessage("每个用户只能下一单");
	                try {
	                    response.outPut(er);
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	                return;
    	}
    	
    	synchronized (lock) {
    		
    		//食物库存不足
        	List<Item> items= cart.getItems();
        	for( Item item : items){
        		if((foodDao.getFood(item.getFoodID())).getStock() - item.getCount() < 0){
        			response.setStatusCode("403","Forbidden");
                    ErrorResult er = new ErrorResult();
                    er.setCode("FOOD_OUT_OF_STOCK");
                    er.setMessage("食物库存不足");
                    try {
                        response.outPut(er);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
        		}
        	}
        
        	//成功
        	OrderResult or = new OrderResult();
        	Order order = new Order((Integer)session.getArtribute(op.getCart_id()), 
        			cart.getItems());
        	foodDao.SubFoodCount(items);
        	orderDao.addOrder(order);
        	or.setId(order.getId().toString());
        	try {
                 response.outPut(or);
    		} catch (Exception e) {
    			e.printStackTrace();
    			System.out.println("下单失败！");
    		}
			
		}
    }

    @Override
    public void doPatch(Request request, Response response) {

    }
}
