package com.hackathon.servlet;

import com.hackathon.dao.CartDao;
import com.hackathon.dao.FoodDao;
import com.hackathon.dao.OrderDao;
import com.hackathon.dao.UserDao;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by beatk on 2015/11/27.
 */
public abstract class Servlet {

    protected UserDao userDao;
    protected FoodDao foodDao;
    protected CartDao cartDao;
    protected OrderDao orderDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public FoodDao getFoodDao() {
        return foodDao;
    }
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setFoodDao(FoodDao foodDao) {
        this.foodDao = foodDao;
    }

    public CartDao getCartDao() {
        return cartDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setCartDao(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    //Servlet实例池
    private static Map<String,Servlet> servlets = new HashMap<String,Servlet>();

    /**
     * 加载并实例化已有的Servlet（通过Servlet）
     * @param ctx       Spring配置上下文对象
     */
    public static void initServlet(ApplicationContext ctx) {

        servlets.put("/login",ctx.getBean("LoginApi",Servlet.class));
        servlets.put("/",ctx.getBean("Welcome",Servlet.class));
        servlets.put("/foods",ctx.getBean("Food",Servlet.class));
        //.....
    }

    /**
     * 获取指定路径的Servlet实例
     * @param urlPattern    Servlet的访问路径
     * @return              Servlet实例
     */
    public static Servlet getServlet(String urlPattern) {
        return servlets.get(urlPattern);
    }

    /**
     * Get方法处理
     * @param request      请求对象
     * @param response      响应对象
     */
    public abstract void doGet(Request request,Response response);


    /**
     * Post方法处理
     * @param request       请求对象
     * @param response       响应对象
     */
    public abstract void doPost(Request request,Response response);

    /**
     * PATCH方法处理
     * @param request       请求对象
     * @param response      相应对象
     */
    public abstract void doPatch(Request request,Response response);
}
