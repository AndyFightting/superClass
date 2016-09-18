package com.example.suguiming.superclass.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.suguiming.superclass.basic.MyApplication;
import com.example.suguiming.superclass.model.StudentModel;
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

    public static final MyApplication application = MyApplication.getInstance();

    private static final String DB_NAME = "superClass.db";
    private Map<String, Dao> daoMap = new HashMap<>();
    private static DatabaseHelper singleHelper;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);//最后一个参数为数据库version
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //----建表-----------------------
            TableUtils.createTable(connectionSource, StudentModel.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            switch (newVersion) {
                // 在不同版本中数据库的变化， 不要 break;
                case 2:

                case 3:

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //单例helper
    public static synchronized DatabaseHelper getHelper() {
        if (singleHelper == null) {
            synchronized (DatabaseHelper.class) {
                if (singleHelper == null)
                    singleHelper = new DatabaseHelper(application);
            }
        }
        return singleHelper;
    }


    public synchronized Dao getDao(Class cla) {
        try {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        super.close();
        daoMap.clear();
    }
}
