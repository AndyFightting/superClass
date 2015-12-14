package com.example.suguiming.superclass.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by suguiming on 15/10/30.
 */

@DatabaseTable(tableName = "user")
public class User implements Serializable {

    @DatabaseField(columnName = "id")
    private long id;

    @DatabaseField(columnName = "phone")
    private String phone;

    @DatabaseField(columnName = "password")
    private String password;

    @DatabaseField(columnName = "token")
    private String token;

    @DatabaseField(columnName = "userName")
    private String userName;

    @DatabaseField(columnName = "avatar")
    private String avatar;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    private String recordDate;




}
