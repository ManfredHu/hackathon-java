package com.hackathon.dao;

import java.util.List;

import com.hackathon.model.Food;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Created by beatk on 2015/11/28.
 */
public class FoodDao extends HibernateDaoSupport {

    /**
     * 根据Food id获取食物实体
     * @param id   食物id
     * @return      返回食物实体
     */
    public Food getFood(Integer id) {
        return getHibernateTemplate().get(Food.class,id);
    }
    
    /**
     * 查询所有食物
     * @return	返回所有食物实体
     */
    public List<Food> getAllFoods() {
    	return (List<Food>)getHibernateTemplate().find("from Food");
    }
    
}
