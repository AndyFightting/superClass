package com.example.suguiming.superclass.database;

import android.content.Context;
import com.example.suguiming.superclass.bean.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by suguiming on 15/11/19.
 */
public class UserDao {

    private Context mContext;
    private DatabaseHelper helper;
    private Dao<User, Integer> userDao;

    public UserDao(Context context){
        mContext = context;
        try {
            helper = DatabaseHelper.getHelper(mContext);
            userDao = helper.getDao(User.class);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void createUser(User user){
        try {
            deleteUser();
            userDao.create(user);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(){
        try {
         List<User> userList = userDao.queryForAll();
            if (userList != null && userList.size() >0){
                return userList.get(0);
            }else {
                return null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteUser(){
        try {
            DeleteBuilder deleteBuilder = helper.getDao(User.class).deleteBuilder();
            deleteBuilder.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user){
        try {
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
