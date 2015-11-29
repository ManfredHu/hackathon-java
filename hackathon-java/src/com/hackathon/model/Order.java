package com.hackathon.model;

import java.util.ArrayList;
import java.util.List;

import com.hackathon.model.Cart.Item;

/**
 * Created by beatk on 2015/11/28.
 */
public class Order {

    private static Integer nextId = 1;

    private String id = "";                                     //订单id号
    private Integer user_id = 0;                                //下单用户id
    private List<Cart.Item> items = new ArrayList<Cart.Item>(); //订单列表
    private Integer total = 0;                                  //订单总价格

    public Order(Integer user_id,List<Cart.Item> items) {

        //获取id时要同步
        synchronized(nextId) {
            id = hashCode() + "";
        }

        //设置其他项
        this.user_id = user_id;
        setItems(items);
    }

    public String getId() {
        return id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public List<Cart.Item> getItems() {
        return items;
    }

    public Integer getTotal() {
        return total;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public void setItems(List<Cart.Item> items) {
        this.items = items;

        //每次设置订单项目时，更新总价格
        this.total = 0;
        for(Cart.Item item : this.items) {
            total += item.getPrice();
        }
    }
}
