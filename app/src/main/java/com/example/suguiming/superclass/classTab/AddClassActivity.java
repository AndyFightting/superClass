package com.example.suguiming.superclass.classTab;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class AddClassActivity extends BaseSwipeActivity {

    private LinearLayout tapLayout;
    private TextView studentTv;
    private TextView dateTv;
    private TextView beginTv;
    private TextView endTv;

    private Date beginDate;
    private Date endDate;
    private int year,month,day;
    private int begin_hour,begin_minute;
    private int end_hour,end_minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        tapLayout = (LinearLayout)findViewById(R.id.tap_layout);
        studentTv = (TextView)findViewById(R.id.student_tv);
        dateTv = (TextView)findViewById(R.id.date_tv);
        beginTv = (TextView)findViewById(R.id.begin_tv);
        endTv = (TextView)findViewById(R.id.end_tv);

        //时间处理
        initTimeData();

//        Map<String,String> result = DateUtil.getRowAndColumn(beginDate);
//        Log.i("hello ",tapTimeStr  + result.get("row")+"-----"+result.get("column"));

    }

    private void initTimeData(){
        String tapTimeStr = getIntent().getStringExtra("taped_date");
        beginDate = DateUtil.getDateFromFullString(tapTimeStr);
        endDate = DateUtil.getEndDate(beginDate);

        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(beginDate);
        year = beginCalendar.get(Calendar.YEAR);
        month = beginCalendar.get(Calendar.MONTH);
        day = beginCalendar.get(Calendar.DAY_OF_MONTH);
        begin_hour = beginCalendar.get(Calendar.HOUR_OF_DAY);
        begin_minute = beginCalendar.get(Calendar.MINUTE);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        end_hour = endCalendar.get(Calendar.HOUR_OF_DAY);
        end_minute = endCalendar.get(Calendar.MINUTE);

        dateTv.setText(DateUtil.getYRstring(beginDate));
        beginTv.setText(DateUtil.getTimeString(beginDate));
        endTv.setText(DateUtil.getTimeString(endDate));

    }

    public void selectStudent(View view){


    }

    public void timeTap(View view){
        switch (view.getId()){
            case R.id.date_tv:{
                //选择日期
                DatePickerDialog datePicker = new DatePickerDialog(this, DateListener, year, month, day);
                datePicker.show();
                break;
            }case R.id.begin_tv:{
                TimePickerDialog timePicker = new TimePickerDialog(this,TimeListener,begin_hour,begin_minute,true);
                timePicker.show();
                break;
            }case R.id.end_tv:{

                break;
            }
        }
    }

    private  DatePickerDialog.OnDateSetListener DateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
            year = selectedYear;
            month = monthOfYear;
            day = dayOfMonth;

            Log.i(year+"-"+monthOfYear+"-"+dayOfMonth,"00000000000000000");
        }
    };

    private TimePickerDialog.OnTimeSetListener TimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {


        }
    };



}
