package com.example.suguiming.superclass.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1; //从0开始算的：一月份是0，12月份是11
    }

    public static int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    //获取某年某月的1号是星期几
    public static int getFirstdayWeekOfMonth(int year,int month) {            //日 一 二 三 四 五 六
        Calendar calendar = Calendar.getInstance();                           //0  1  2  3  4  5 6
        calendar.set(year, month - 1, 1);

        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        weekDay = weekDay - 1;
        return weekDay;
    }

    //某年某月有多少天
    public static int getDaysInMonth(int year, int month) {
        if (month > 12) {
            month = 1;
            year += 1;
        } else if (month < 1) {
            month = 12;
            year -= 1;
        }
        int[] arr = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        int days = 0;

        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            arr[1] = 29; // 闰年2月29天
        }

        try {
            days = arr[month - 1];
        } catch (Exception e) {
            e.getStackTrace();
        }

        return days;
    }

   //是否为同一天
    public static boolean isSameDay(Date date1,Date date2){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String ds1 = sdf.format(date1);
        String ds2 = sdf.format(date2);
        if (ds1.equals(ds2)) {
            return true;
        } else {
            return false;
        }
    }

   //是否为周末
    public static boolean isWeekend(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week == 1 || week == 7){
            return true;
        }

        return false;
    }

    //星期天第一列，从1开始
    public static String stringOfWeek(int week){
        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
        return weeks[week-1];
    }

    //yyyy-MM-dd HH:mm:ss   转  date
    public static Date stringToDate(String timeStr){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formater.parse(timeStr);
        }catch (Exception e){
          e.printStackTrace();
        }
       return null;
    }

   //date  转  yyyy-MM-dd HH:mm:ss
    public static String dateToString(Date date){
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         return   formater.format(date);
    }

    //date 得到 MM月dd日
    public static String getYRstring(Date date){ // YR -- 月日的意思
        SimpleDateFormat formater = new SimpleDateFormat("MM月dd日");
        return   formater.format(date);
    }

    //date 得到 11:00
    public static String getTimeString(Date date){
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        return   formater.format(date);
    }

    //排期中根据点击得到点击的 date
    public static Date getDateFromTap(Date beginDate,int row ,int column,boolean hasHalf){//行，列 从1 开始
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        calendar.add(Calendar.DAY_OF_YEAR, column - 1);
        calendar.set(Calendar.HOUR_OF_DAY,row+5);
        if (hasHalf){
            calendar.set(Calendar.MINUTE,30);
        }else {
            calendar.set(Calendar.MINUTE,0);
        }
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    //时间起点 06:00  这里的row 1小时分两段
    //排期中根据点date 得到对应 row column都从1 开始
     public static Map<String,String> getRowAndColumn(Date selectedDate){
         Map<String,String> resultMap = new HashMap<>();

         int column;
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(selectedDate);
         int tmpNum = calendar.get(Calendar.DAY_OF_WEEK)-1;//DAY_OF_WEEK说明：calendar周日为一周的第一天==1，周六最后一天 ==7
         if (tmpNum == 0){
             tmpNum =7;
         }
         column = tmpNum;
         resultMap.put("column",column+"");

         int row;
         int beginHour = 6*2;//hour 1小时分两段
         String timeStr = DateUtil.getTimeString(selectedDate);//如11:30
         int hour = Integer.parseInt(timeStr.substring(0,2))*2;//这里的hour 1小时分两段
         row = hour-beginHour+1;

         int minute = Integer.parseInt(timeStr.substring(3));
         if (minute == 30){
             row++;
         }
         resultMap.put("row", row + "");

         return resultMap;
     }

    //点击排期时默认的endDate 是当前时间加1小时
    public static Date getEndDate(Date beginDate){
        String timeStr = DateUtil.getTimeString(beginDate);
        int beginHour = Integer.parseInt(timeStr.substring(0, 2));
        int beginMinute = Integer.parseInt(timeStr.substring(3));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        if (beginHour == 22 && beginMinute == 30){
            calendar.add(Calendar.MINUTE, 30);
        }else {
            calendar.add(Calendar.HOUR_OF_DAY, +1);
        }
        return calendar.getTime();
    }

    public static Date dateFromDetail(int year,int month,int day,int hour,int minute){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,minute);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }

    //把1 变成 01
    public static String getNumStrig(int num) {
        if (0 <= num && num <= 9) {
            return "0" + num;
        } else {
            return num + "";
        }
    }

}
