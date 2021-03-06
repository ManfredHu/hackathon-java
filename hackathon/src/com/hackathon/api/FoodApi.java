package com.hackathon.api;

import java.io.IOException;
import java.util.List;

import org.aspectj.weaver.ast.Var;

import com.google.gson.Gson;
import com.hackathon.dao.FoodDao;
import com.hackathon.model.Food;
import com.hackathon.param.LoginParam;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.Session;

public class FoodApi extends Servlet {

	@Override
	public void doGet(Request request, Response response) {
		// TODO Auto-generated method stub
		List<Food> listFdFoods = foodDao.getAllFoods();
		
		try {
			response.outPut(listFdFoods);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("789"+str);
		
	}

	@Override
	public void doPost(Request request, Response response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doPatch(Request request, Response response) {
		// TODO Auto-generated method stub

	}

}
