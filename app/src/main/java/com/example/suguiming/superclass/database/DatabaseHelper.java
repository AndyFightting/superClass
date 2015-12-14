package com.example.suguiming.superclass.database;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.example.suguiming.superclass.bean.User;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suguiming on 15/11/19.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DB_NAME = "superClass.db";
    private Map<String, Dao> daoMap = new HashMap<>();
    private static DatabaseHelper singleHelper;

    public DatabaseHelper(Context context){
        super(context, DB_NAME, null, 1);//最后一个参数为数据库version
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
       try {
           //----建表-----------------------
           TableUtils.createTable(connectionSource, User.class);

       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //数据库更新时候用的--------------


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //单例helper
    public static synchronized DatabaseHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (singleHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (singleHelper == null)
                    singleHelper = new DatabaseHelper(context);
            }
        }
        return singleHelper;
    }


    public synchronized Dao getDao(Class cla) throws SQLException {
        Dao dao = null;
        String className = cla.getSimpleName();

        if (daoMap.containsKey(className)) {
            dao = daoMap.get(className);
        }
        if (dao == null) {
            dao = super.getDao(cla);
            daoMap.put(className, dao);
        }
        return dao;
    }

    @Override
    public void close() {
        super.close();

        for (String key : daoMap.keySet()) {
            Dao dao = daoMap.get(key);
            dao = null;
        }
    }
}
