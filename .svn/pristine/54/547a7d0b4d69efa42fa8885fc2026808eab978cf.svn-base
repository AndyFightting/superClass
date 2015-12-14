package com.example.suguiming.superclass.classTab.calendar;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.suguiming.superclass.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by suguiming on 15/10/23.
 */
public class SGMCalendar extends RelativeLayout{

    public enum MoveFlag{
        NONE,LEFT,RIGHT
    }

    MoveFlag moveFlag;
    private TextView monthTv;
    private LayoutInflater inflater;
    private ViewPager weekViewPager;//最上方的星期列表
    private List<CalendarWeek> weeks;

    //左边时间列表
    private SGMScrollView timeScrollView;
    private LinearLayout timeLayout;

    private ViewPager classViewPager;
    List<CalendarWeekClass> weekClasses;


    public SGMCalendar(Context context, AttributeSet attributeSet){
        super(context,attributeSet);

        inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar, this);//用calendar.xml文件做为界面
        monthTv = (TextView)findViewById(R.id.calendar_month);

        initWeekView();
        initTimeView();
        initClassView();
    }

    private void initWeekView(){
        weekViewPager = (ViewPager)findViewById(R.id.calendar_week);
        weeks = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//把calendar设置成周几
        calendar.add(Calendar.DAY_OF_YEAR, -7);

        for (int i=0;i<3;i++){
            CalendarWeek week = (CalendarWeek)inflater.inflate(R.layout.calendar_week_page,null);
            week.setBeginDate(calendar.getTime());
            weeks.add(week);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        weekViewPager.setAdapter(new WeekPageAdapter(weeks));
        weekViewPager.setCurrentItem(1);

        weekViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                classViewPager.setScrollX(weekViewPager.getScrollX());
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    moveFlag = MoveFlag.RIGHT;
                }
                if (position == 2) {
                    moveFlag = MoveFlag.LEFT;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {//滚动结束了
                    if (moveFlag == MoveFlag.RIGHT) {
                        doMoveRight();//手指往右滑动
                    } else if (moveFlag == MoveFlag.LEFT) {
                        doMoveLeft();//手指往左边滑动
                    }
                }
            }
        });
        setCurrentMonth();
    }

    private void doMoveRight(){
        moveFlag = MoveFlag.NONE;
      //上方日期处理
        Calendar calendar = Calendar.getInstance();

        CalendarWeek week0 = weeks.get(0);
        CalendarWeek week1 = weeks.get(1);
        CalendarWeek tmpWeek = weeks.get(2); //时间处理后移动到0位

        calendar.setTime(week0.getBeginDate());
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        tmpWeek.setBeginDate(calendar.getTime());

        weeks.set(0, tmpWeek);
        weeks.set(1, week0);
        weeks.set(2, week1);
        weekViewPager.setAdapter(new WeekPageAdapter(weeks));
        weekViewPager.setCurrentItem(1);

        //处理class ViewPager
        CalendarWeekClass weekClass0 = weekClasses.get(0);
        CalendarWeekClass weekClass1 = weekClasses.get(1);
        CalendarWeekClass tmpWeekClass = weekClasses.get(2);//理后移动到0位
        tmpWeekClass.setBeginDate(calendar.getTime());

        weekClasses.set(0,tmpWeekClass);
        weekClasses.set(1,weekClass0);
        weekClasses.set(2,weekClass1);
        classViewPager.setAdapter(new WeekClassPageAdapter(weekClasses));
        classViewPager.setCurrentItem(1);

        setCurrentMonth();
    }


    private void doMoveLeft(){
        moveFlag = MoveFlag.NONE;
        //上方日期处理
        Calendar calendar = Calendar.getInstance();

        CalendarWeek tmpWeek = weeks.get(0);//时间处理后移动到2位
        CalendarWeek week1 = weeks.get(1);
        CalendarWeek week2 = weeks.get(2);

        calendar.setTime(week2.getBeginDate());
        calendar.add(Calendar.DAY_OF_YEAR, 7);
        tmpWeek.setBeginDate(calendar.getTime());

        weeks.set(0, week1);
        weeks.set(1, week2);
        weeks.set(2,tmpWeek);
        weekViewPager.setAdapter(new WeekPageAdapter(weeks));
        weekViewPager.setCurrentItem(1);

        //处理class ViewPager
        CalendarWeekClass tmpWeekClass = weekClasses.get(0);//理后移动到2位
        CalendarWeekClass weekClass1 = weekClasses.get(1);
        CalendarWeekClass  weekClass2= weekClasses.get(2);
        tmpWeekClass.setBeginDate(calendar.getTime());

        weekClasses.set(0,weekClass1);
        weekClasses.set(1,weekClass2);
        weekClasses.set(2,tmpWeekClass);
        classViewPager.setAdapter(new WeekClassPageAdapter(weekClasses));
        classViewPager.setCurrentItem(1);

        setCurrentMonth();
    }

    private void setCurrentMonth(){
        CalendarWeek week = weeks.get(1);
        Date beginDate = week.getBeginDate();
        SimpleDateFormat format = new SimpleDateFormat("MM月");
        monthTv.setText(format.format(beginDate));
    }

    private void initTimeView(){
        timeScrollView = (SGMScrollView)findViewById(R.id.time_scroll);
        timeLayout = (LinearLayout)findViewById(R.id.time_layout);
        timeLayout.removeAllViewsInLayout();

        for(int i=6;i<=22;i++) {
            String tmpTime;
            if (i<10){
                tmpTime = "0"+i+":00";
            }else {
                tmpTime = i+":00";
            }

            RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.calendar_time_item, null);
            TextView timeTv = (TextView)layout.findViewById(R.id.time_item_tv);
            timeTv.setText(tmpTime);

            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(java.util.Calendar.HOUR_OF_DAY);
            if (i == hour){
                timeTv.setTextColor(getResources().getColor(R.color.red));
            }
            timeLayout.addView(layout);
        }

        //滚动带动weekClassView
        timeScrollView.setScrollingListener(new SGMScrollView.ScrollingListener() {
            @Override
            public void onScrolling(int l, int t, int oldl, int oldt) {
                for (CalendarWeekClass weekClass:weekClasses){
                    weekClass.setScrollY(timeScrollView.getScrollY());
                    Log.i("sdfsdf","");
                }
            }
        });
    }

    private void initClassView(){
        classViewPager = (ViewPager)findViewById(R.id.class_pager);
        weekClasses = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//把calendar设置成周几
        calendar.add(Calendar.DAY_OF_YEAR, -7);

        for(int i=0;i<3;i++) {
            CalendarWeekClass classView = (CalendarWeekClass)inflater.inflate(R.layout.calendar_week_class, null);
            classView.setBeginDate(calendar.getTime());//这里开始初始化view
            weekClasses.add(classView);
            calendar.add(Calendar.DAY_OF_YEAR, 7);
        }
        classViewPager.setAdapter(new WeekClassPageAdapter(weekClasses));
        classViewPager.setCurrentItem(1);

        classViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
                weekViewPager.setScrollX(classViewPager.getScrollX());
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    moveFlag = MoveFlag.RIGHT;
                }
                if (position == 2) {
                    moveFlag = MoveFlag.LEFT;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {//滚动结束了
                    if (moveFlag == MoveFlag.RIGHT) {
                        doMoveRight();//手指往右滑动
                    } else if (moveFlag == MoveFlag.LEFT) {
                        doMoveLeft();//手指往左边滑动
                    }
                }
            }
        });

        //class view 设置滚动监听联动time scroll
        for (CalendarWeekClass weekClass : weekClasses){
            weekClass.setScrollingListener(new SGMScrollView.ScrollingListener() {
                @Override
                public void onScrolling(int l, int t, int oldl, int oldt) {
                    timeScrollView.setScrollY(t);
                }
            });

        }

    }

}
