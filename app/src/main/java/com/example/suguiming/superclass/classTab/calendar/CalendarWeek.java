package com.example.suguiming.superclass.classTab.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by suguiming on 15/10/23.
 */
public class CalendarWeek extends LinearLayout {

    List<View> days;
    private Date beginDate;

    public CalendarWeek(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_calendar_week, this);

        days = new ArrayList<>();
        days.add(findViewById(R.id.week_day1));
        days.add(findViewById(R.id.week_day2));
        days.add(findViewById(R.id.week_day3));
        days.add(findViewById(R.id.week_day4));
        days.add(findViewById(R.id.week_day5));
        days.add(findViewById(R.id.week_day6));
        days.add(findViewById(R.id.week_day7));
    }

    public void setBeginDate(Date beginDate){
        this.beginDate = beginDate;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);
        for (View day:days){
            int week = calendar.get(Calendar.DAY_OF_WEEK);

            //几号
            SimpleDateFormat format = new SimpleDateFormat("dd");
            String dayNumber = format.format(calendar.getTime());
            TextView dayTextView = (TextView)day.findViewById(R.id.day_number);
            dayTextView.setText(dayNumber);
            setTextColorForDate(dayTextView,calendar.getTime());

            //周几
            String weekString = DateUtil.stringOfWeek(week);
            TextView weekdayTextView = (TextView)day.findViewById(R.id.day_weekday);
            weekdayTextView.setText(weekString);
            setTextColorForDate(weekdayTextView,calendar.getTime());

            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
    }

    private void setTextColorForDate(TextView textView,Date date){
        Date today = new Date();
        if (DateUtil.isSameDay(today, date)){
            textView.setTextColor(this.getResources().getColor(R.color.calendar_day_current));
            return;
        }

        if (DateUtil.isWeekend(date)){
            textView.setTextColor(this.getResources().getColor(R.color.calendar_day_weekend));
            return;
        }

        textView.setTextColor(this.getResources().getColor(R.color.calendar_day_default));
    }

    public Date getBeginDate(){
        return this.beginDate;
    }
}
