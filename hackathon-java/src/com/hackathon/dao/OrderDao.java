package com.hackathon.dao;

import com.hackathon.model.Food;
import com.hackathon.model.Order;
import com.hackathon.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by beatk on 2015/11/29.
 */
public class OrderDao {

    private static Map<Integer,Order> orders = new HashMap<>();

    /**
     * 添加新的订单，如果订单id已存在，则不添加
     * @param order 订单实体
     */
    public void addOrder(Order order) {

        if(orders.get(order.getId()) != null) return;
        orders.put(order.getId(),order);
    }

    /**
     * 更新订单（一般不调用）
     * @param order 订单实体
     * @return      是否成功更新
     */
    public boolean updateOrder(Order order) {

        if(orders.get(order.getId()) == null) return false;
        orders.put(order.getId(),order);
        return true;
    }

    /**
     * 删除订单
     * @param order 要删除的订单
     * @return      是否成功删除
     */
    public boolean deleteOrder(Order order) {

        if(orders.get(order.getId()) == null) return false;
        orders.remove(order.getId());
        return true;
    }

    /**
     * 根据订单id获取订单号
     * @param id    要获取的订单id
     * @return      订单实体（如果不存在则返回会null）
     */
    public Order getOrder(Integer id) {

        return orders.get(id);
    }
    
    /**
     * 获取全部订单
     * @return      全部订单实体
     */
    public List<Order> getAllOrders() {
    	return (List<Order>)orders.values();
    }
}
