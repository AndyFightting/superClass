package com.example.suguiming.superclass.model;

import android.support.annotation.Nullable;

import com.example.suguiming.superclass.database.DatabaseHelper;
import com.example.suguiming.superclass.utils.CommonUtil;
import com.example.suguiming.superclass.utils.DateUtil;
import com.example.suguiming.superclass.utils.OttoUtil;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by suguiming on 15/10/30.
 */

@DatabaseTable(tableName = "StudentModel")
public class StudentModel {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "idString",uniqueIndexName = "")
    public String idString;//不空,唯一字符串ID

    @DatabaseField(columnName = "nameString")
    public String nameString;//不空,唯一

    @DatabaseField(columnName = "gender")//0默认,1男,2女
    public int gender;

    @DatabaseField(columnName = "phoneString",defaultValue = "")
    public String phoneString;

    @DatabaseField(columnName = "remainCount")
    public int remainCount;

    @DatabaseField(columnName = "finishCount")
    public int finishCount;

    @DatabaseField(columnName = "remarkString",defaultValue = "")
    public String remarkString;

    @DatabaseField(columnName = "createTime",defaultValue = "2000-10-10 00:00:00")
    private String createTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIdString() {
        return idString;
    }

    public void setIdString(String idString) {
        this.idString = idString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getPhoneString() {
        return phoneString;
    }

    public void setPhoneString(String phoneString) {
        this.phoneString = phoneString;
    }

    public int getRemainCount() {
        return remainCount;
    }

    public void setRemainCount(int remainCount) {
        this.remainCount = remainCount;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(int finishCount) {
        this.finishCount = finishCount;
    }

    public String getRemarkString() {
        return remarkString;
    }

    public void setRemarkString(String remarkString) {
        this.remarkString = remarkString;
    }


    //-------与数据库无关的属性------------------------------------------
    public String notifyType;

    public static String ADD = "ADD_STUDENT";
    public static String UPDATE = "UPDATE_STUDENT";
    public static String DELETE = "DELETE_STUDENT";

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    //--------数据库处理-----------------------------------------------
    private static DatabaseHelper helper = DatabaseHelper.getHelper();
    private static Dao<StudentModel, Integer> studentDao = helper.getDao(StudentModel.class);

    public static void addStudent(StudentModel model, boolean notify) {
        try {
            if (model == null){
                return;
            }

            if (CommonUtil.isEmpty(model.idString)) {
                model.idString = CommonUtil.getUniqueString(12);
            }
            model.createTime = DateUtil.getFullStringFromDate(new Date());

            studentDao.createIfNotExists(model);

            if (notify){
                OttoUtil.studentAdd(model);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStudent(StudentModel model,boolean notify) {
        try {
            if (model == null){
                return;
            }

            studentDao.update(model);

            if (notify){
                OttoUtil.studentUpdate(model);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Nullable //使用要判断是不是空!!
    public static StudentModel getStudent(String idString) {
        try {
            QueryBuilder<StudentModel, Integer> queryBuilder = studentDao.queryBuilder();
            Where<StudentModel, Integer> where = queryBuilder.where();
            where.eq("idString", idString);

            List<StudentModel> modelList = queryBuilder.query();
            if (modelList != null && modelList.size() > 0) {
                return modelList.get(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isIn(String nameString) {
        if (CommonUtil.isEmpty(nameString)){
            return false;
        }

        List<StudentModel> list = getStudents(nameString.trim());
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public static List<StudentModel> getStudents(String nameString) {
        try {
            QueryBuilder<StudentModel, Integer> queryBuilder = studentDao.queryBuilder();
            Where<StudentModel, Integer> where = queryBuilder.where();
            where.eq("nameString", nameString);

            List<StudentModel> modelList = queryBuilder.query();
            if (modelList != null && modelList.size() > 0) {
                return modelList;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<StudentModel> getAllStudents() {
        try {
            List<StudentModel> modelList = studentDao.queryForAll();
            if (modelList != null && modelList.size() > 0) {
                return modelList;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void deleteStudent(String idString,boolean notify) {
        try {
            StudentModel model = getStudent(idString);

            DeleteBuilder<StudentModel, Integer> deleteBuilder = studentDao.deleteBuilder();
            Where<StudentModel, Integer> where = deleteBuilder.where();
            where.eq("idString", idString);
            deleteBuilder.delete();

            //------下面删除与该学员有关的信息------



            if (notify && model!=null){
                OttoUtil.studentDelete(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllStudents() {
        try {
            studentDao.delete(studentDao.queryForAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
