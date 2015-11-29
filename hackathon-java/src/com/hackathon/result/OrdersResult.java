package com.hackathon.result;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//查询订单
public class OrdersResult {
	
	private String id = "";
	private List<Item> items = new ArrayList<Item>();
	private Integer total = 0;
	
	//Item类
	class Item{
		private int food_id = 0;
		private int count = 0;
		public int getFood_id() {
			return food_id;
		}
		public void setFood_id(int food_id) {
			this.food_id = food_id;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
	}
	
	public void addItem(int food_id,int count){
		Item item = new Item();
		item.food_id = food_id;
		item.count = count;
		items.add(item);
		
	}

	public List<Item> getItems() {
		return items;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
