package com.hackathon.dao;

import com.hackathon.model.User;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * Created by beatk on 2015/11/28.
 */
public class UserDao extends HibernateDaoSupport {

    /**
     * 根据user_id获取user实体
     * @param id    user_id
     * @return      User实体
     */
    public User getUser(Integer id) {
        return getHibernateTemplate().get(User.class,id);
    }

    /**
     * 根据username获取用户实体
     * @param username     用户名
     * @return             如果存在该用户，返回对应User信息，否则返回null
     */
    public User getUser(String username) {

        List<User> users = (List<User>)getHibernateTemplate()
                .find("from User as u where u.name=?",username);
        if(users.size() == 0 ) {
            return null;
        }
        return users.get(0);
    }

}
