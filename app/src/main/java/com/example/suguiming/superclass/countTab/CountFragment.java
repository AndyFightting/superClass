package com.example.suguiming.superclass.countTab;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suguiming.superclass.MainActivity;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseFragment;
import com.example.suguiming.superclass.utils.DateUtil;
import com.example.suguiming.superclass.classTab.calendar.SGMCalendar;

import java.util.ArrayList;
import java.util.List;

public class CountFragment extends BaseFragment implements View.OnClickListener{

    public MainActivity mainActivity;

    private TextView titleTv;
    private TextView preMonthBt;
    private TextView nextMonthBt;
    private ViewPager countPager;
    List<CountCalendar> calendarViewList;

    SGMCalendar.MoveFlag moveFlag;
    private int centerYear,centerMonth;
    private int preMonth,preMonthYear;
    private int nextMonth,nextMonthYear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        centerYear = DateUtil.getCurrentYear();
        centerMonth = DateUtil.getCurrentMonth();
        resetPreAndNextMonth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_count, container, false);

        calendarViewList = new ArrayList<>();
        for (int i=0;i<3;i++){
            CountCalendar calendarView = (CountCalendar)inflater.inflate(R.layout.calendar_count_page,null);
            if(i==0){
                calendarView.setYearMonthDay(preMonthYear, preMonth, 1);
            }else if(i==1){
                calendarView.setYearMonthDay(centerYear, centerMonth, DateUtil.getCurrentDay());
            }else {
                calendarView.setYearMonthDay(nextMonthYear, nextMonth, 1);
            }
            calendarViewList.add(calendarView);

            //上个月或下个月的day item 点击监听
            calendarView.setOnPreOrNextMonthTapListener(new CountCalendar.OnPreOrNextMonthTapListener() {
                @Override
                public void preMonthDayTap(int day) {
                    doMoveRight();
                    centerCalendarTapedAtDay(day);
                }
                @Override
                public void nextMonthDayTap(int day) {
                    doMoveLeft();
                    centerCalendarTapedAtDay(day);
                }
            });

        }

        countPager = (ViewPager)view.findViewById(R.id.count_pager);
        countPager.setAdapter(new CountCalendarAdapter(calendarViewList));
        countPager.setCurrentItem(1);

        countPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    moveFlag = SGMCalendar.MoveFlag.RIGHT;
                }
                if (position == 2) {
                    moveFlag = SGMCalendar.MoveFlag.LEFT;
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 0) {//滚动结束了
                    if (moveFlag == SGMCalendar.MoveFlag.RIGHT) {
                        doMoveRight();//手指往右滑动
                    } else if (moveFlag == SGMCalendar.MoveFlag.LEFT) {
                        doMoveLeft();//手指往左边滑动
                    }
                }
            }
        });

        titleTv = (TextView)view.findViewById(R.id.title_tv);
        titleTv.setText(String.format("%d年%d月", centerYear, centerMonth));

        preMonthBt = (TextView)view.findViewById(R.id.pre_month_bt);
        nextMonthBt = (TextView)view.findViewById(R.id.next_month_bt);
        preMonthBt.setOnClickListener(this);
        nextMonthBt.setOnClickListener(this);

        return view;
    }
    private void doMoveRight() {//手指往右边滑动,上个月
        moveFlag = SGMCalendar.MoveFlag.NONE;
        preMonthTaped();

        CountCalendar calendar0 = calendarViewList.get(0);
        CountCalendar calendar1 = calendarViewList.get(1);
        CountCalendar tmpCalendar = calendarViewList.get(2);//处理后移到0位
        tmpCalendar.setYearMonthDay(preMonthYear, preMonth, 1);

        calendarViewList.set(0, tmpCalendar);
        calendarViewList.set(1,calendar0);
        calendarViewList.set(2,calendar1);

        countPager.setAdapter(new CountCalendarAdapter(calendarViewList));
        countPager.setCurrentItem(1);

    }
    private void doMoveLeft() {//手指往左边滑动,下个月
        moveFlag = SGMCalendar.MoveFlag.NONE;
        nextMonthTaped();

        CountCalendar tmpCalendar = calendarViewList.get(0);//处理后移到2位
        CountCalendar calendar1 = calendarViewList.get(1);
        CountCalendar calendar2 = calendarViewList.get(2);
        tmpCalendar.setYearMonthDay(nextMonthYear, nextMonth, 1);

        calendarViewList.set(0,calendar1);
        calendarViewList.set(1,calendar2);
        calendarViewList.set(2,tmpCalendar);

        countPager.setAdapter(new CountCalendarAdapter(calendarViewList));
        countPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.pre_month_bt:{//点击上个月
                doMoveRight();
                break;
            }
            case R.id.next_month_bt:{//点击下个月
                doMoveLeft();
                break;
            }
        }
    }

    private void preMonthTaped(){
        centerMonth = preMonth;
        centerYear = preMonthYear;
        resetPreAndNextMonth();
    }
    private void nextMonthTaped(){
        centerMonth = nextMonth;
        centerYear = nextMonthYear;

        resetPreAndNextMonth();
    }


    private void resetPreAndNextMonth() {
        preMonth = centerMonth - 1;
        preMonthYear = centerYear;
        if (preMonth == 0) {
            preMonth = 12;
            preMonthYear = centerYear - 1;
        }

        nextMonth = centerMonth + 1;
        nextMonthYear = centerYear;
        if (nextMonth == 13) {
            nextMonth = 1;
            nextMonthYear = centerYear + 1;
        }
        if (titleTv != null){
            titleTv.setText(String.format("%d年%d月", centerYear, centerMonth));
        }
    }

    private void centerCalendarTapedAtDay(int day){
        CountCalendar centerCalendar = calendarViewList.get(1);
        centerCalendar.clearLastSelectedStyle();
        centerCalendar.setCenterCalendarTapDay(day);
    }

}
