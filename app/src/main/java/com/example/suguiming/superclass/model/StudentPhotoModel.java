package com.example.suguiming.superclass.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by suguiming on 16/9/20.
 */

//学员相册记录modle
@DatabaseTable(tableName = "StudentPhotoModel")
public class StudentPhotoModel {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = "studentIdString",uniqueIndexName = "")
    public String studentIdString;//不空,唯一字符串ID,每个学员有一个以该字符串命名的文件夹,照片放里面

    @DatabaseField(columnName = "photoName")
    public String photoName;

    @DatabaseField(columnName = "createTime",defaultValue = "2000-10-10 00:00:00")
    private String createTime;

    public String getStudentIdString() {
        return studentIdString;
    }

    public void setStudentIdString(String studentIdString) {
        this.studentIdString = studentIdString;
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}
