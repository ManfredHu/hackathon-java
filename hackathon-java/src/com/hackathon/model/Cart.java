package com.hackathon.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beatk on 2015/11/28.
 * 购物车Model
 */
public class Cart {

    private String id = "";                         //购物车id
    private List<Item> items = new ArrayList<Item>();   //购物车物品

    public Cart() {
        this.id = this.hashCode() + "";
    }

    public static class Item {

        private Integer foodID = 0;     //食物ID
        private Integer count = 0;      //食物数量
        private Integer price = 0;      //食物价格

        public Item(Integer foodID,Integer count,Integer price) {
            this.foodID = foodID;
            this.count = count;
            this.price = price;
        }

        public Integer getFoodID() {
            return foodID;
        }

        public Integer getCount() {
            return count;
        }

        public void setFoodID(Integer foodID) {
            this.foodID = foodID;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public Item[] getItems() {
        Item[] itema = new Item[3];
        return this.items.toArray(itema);
    }

    /**
     * 根据foodID获取Item
     * @param foodID    foodID
     * @return          如果存在，返回指定Item，否则返回null
     */
    public Item getItem(Integer foodID) {

        Item item;
        for (int i = 0; i < items.size(); i++) {
            item = items.get(i);
            if(item.getFoodID().equals(foodID)) {
                return item;
            }
        }
        return null;
    }

    /**
     * 同步方法（防止单个用户多条线程访问出现并发错误），添加item
     * @param foodID    食物id
     * @param count     数量
     * @param price     价钱
     * @return          返回是否成功添加，成功返回true，失败返回false(购物车食品超过3件)
     */
    public synchronized boolean addItems(Integer foodID,Integer count,Integer price) {

        int itemsCount = 0;

        //添加的数量不正确，返回false
        if(count <= 0) return false;

        for (Item item : items) {
            itemsCount += item.getCount();
        }

        //已经有3件，返回false
        if(itemsCount == 3) {
            return false;
        }

        //购入后超过3件，返回false
        if((itemsCount += count) > 3) {
            return false;
        }

        items.add(new Item(foodID,count,price));
        return true;
    }

}
