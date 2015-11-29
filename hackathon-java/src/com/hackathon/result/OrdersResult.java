package com.hackathon.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//查询订单
public class OrdersResult {
	
	private String id = "";
	private List<Map<String,Integer>> items = new ArrayList<Map<String,Integer>>();
	private Integer total = 0;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Map<String, Integer>> getItems() {
		return items;
	}
	public void setItems(List<Map<String, Integer>> items) {
		this.items = items;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
