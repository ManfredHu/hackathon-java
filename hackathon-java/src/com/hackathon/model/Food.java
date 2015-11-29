package com.hackathon.model;

/**
 * Created by beatk on 2015/11/28.
 */
public class Food {

    private Integer id;         //食物ID
    private Integer stock;      //食物储量
    private Integer  price;      //食物价格

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }


    public Integer getId() {

        return id;
    }

    public Integer getStock() {
        return stock;
    }

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
    
}
