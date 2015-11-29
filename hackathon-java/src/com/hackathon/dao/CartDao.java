package com.hackathon.dao;

import com.hackathon.model.Cart;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beatk on 2015/11/29.
 */
public class CartDao {

    //内存数据库（购物车）
    private static Map<String,Cart> carts = new HashMap<String,Cart>();
    
    //获取内存数据库
    public Map<String,Cart> returnCarts(){
    	return carts;
    }

    /**
     * 添加新的购物车，如果购物车已存在（相同ID的购物车）
     * 则数据不做更改
     * @param cart  购物车对象
     */
    public void addCart(Cart cart) {

        //如果存在该ID的购物车，则立即返回
        if(carts.get(cart.getId()) != null)
            return;
        carts.put(cart.getId(),cart);
    }

    /**
     * 更新购物车（由于是内存数据库，一般不会调用此方法）
     * @param cart  购物车对象
     * @return      是否更新成功
     */
    public boolean updateCart(Cart cart) {

        if(carts.get(cart.getId()) == null) return false;
        carts.put(cart.getId(),cart);
        return true;
    }


    /**
     * 删除购物车
     * @param cart  要删除的购物车对象
     * @return      是否成功删除
     */
    public boolean deleteCart(Cart cart) {

        if(carts.get(cart.getId()) == null) return false;
        carts.remove(cart.getId());
        return true;
    }

    /**
     * 根据购物车id获取购物车对象
     * @param id    购物车id
     * @return      对应id的购物车对象(如果不存在则返回null)
     */
    public Cart getCart(String id) {

        return carts.get(id);
    }
}
