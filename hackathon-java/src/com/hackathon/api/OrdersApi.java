package com.hackathon.api;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.hackathon.dao.CartDao;
import com.hackathon.model.Cart;
import com.hackathon.model.Cart.Item;
import com.hackathon.param.OrderParam;
import com.hackathon.result.ErrorResult;
import com.hackathon.result.OrderResult;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;


/**
 * Created by beatk on 2015/11/29.
 */
public class OrdersApi extends Servlet {

    @Override
    public void doGet(Request request, Response response) {

    }

    @Override
    public void doPost(Request request, Response response) {
    		
    	String data = request.getData();
    	Gson gson = new Gson();
    	OrderParam op = new OrderParam();
    	
    	//分解所传递的数据
    	op = gson.fromJson(data, OrderParam.class);
    	
    	Map<String,Cart> cart  = cartDao.returnCarts();
    	Set keys = cart.keySet();
    	
    	//篮子不存在时，返回404错误
    	if(cartDao.getCart(data)==null){
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
    	String param = request.getParameter("access_token");
    	Session session = request.getSession();
    	if(!param.equals(session.getArtribute("userId"))){
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
    	
    	//食物库存不足
    	Cart userCart = cartDao.getCart(data);
    	Item[] items= userCart.getItems();
    	for( Item item : items){
    		if((foodDao.getFood(item.getFoodID())).getStock()<=0){
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
    	
    	//超过下单次数限制
    	
    	//成功
    	OrderResult or = new OrderResult();
    	or.setId(id);
    	try {
    		 response.setStatusCode("200","OK");
             response.outPut(or);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("输出200失败！");
		}
    }

    @Override
    public void doPatch(Request request, Response response) {

    }
}
