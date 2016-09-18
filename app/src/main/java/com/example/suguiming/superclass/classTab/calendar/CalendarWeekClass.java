package com.example.suguiming.superclass.classTab.calendar;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.utils.DateUtil;
import com.example.suguiming.superclass.classTab.AddClassActivity;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by suguiming on 15/10/26.
 */
public class CalendarWeekClass extends SGMScrollView implements View.OnClickListener {

    private Date beginDate;
    public ScrollView scrollView;
    public RelativeLayout layout;
    private LayoutInflater inflater;

    public int scrollWidth;
    public int cellWidth;
    public int cellHeight;

    public CalendarWeekClass(Context context,AttributeSet attributeSet){
        super(context, attributeSet);

        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);

        scrollWidth =dm.widthPixels-100;
        cellWidth = scrollWidth/7;
        cellHeight = 100;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.component_calendar_week_class, this);

        scrollView = (ScrollView)findViewById(R.id.class_scroll);
        layout = (RelativeLayout)findViewById(R.id.class_layout);
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
        layout.removeAllViews();

        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(beginDate);

        for(int i=0;i<7;i++){ //列
            Date tmpDate = calendar.getTime();
            boolean isToday = DateUtil.isSameDay(today,tmpDate);

            for(int j=0;j<17;j++){//行
                RelativeLayout tmpView = (RelativeLayout)inflater.inflate(R.layout.class_item,null);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(cellWidth,cellHeight);
                params.leftMargin = cellWidth*i;
                params.topMargin = cellHeight*j;

                View backView = tmpView.findViewById(R.id.back_view);

                if (isToday){
                    backView.setBackgroundResource(R.color.light_green);
                }

                TextView upTv = (TextView)tmpView.findViewById(R.id.up_tv);
                upTv.setText(i + 1 + 7 * (j) + "");
                upTv.setOnClickListener(this);

                TextView downTv = (TextView)tmpView.findViewById(R.id.down_tv);
                downTv.setText(i+1+7*(j)+"");
                downTv.setOnClickListener(this);

                layout.addView(tmpView, params);
            }
            calendar.add(Calendar.DAY_OF_YEAR,1);
        }
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.up_tv:{//整点的
                int row,column;//行，列 从1 开始算
                TextView tmpTv = (TextView)view;

                int num = Integer.parseInt(tmpTv.getText().toString());
                row = (num+6)/7;
                column = num-(row-1)*7;

                Date tapDate = DateUtil.getDateFromTap(beginDate, row, column, false);
                Intent intent = new Intent(getContext(), AddClassActivity.class);
                intent.putExtra("taped_date",DateUtil.dateToString(tapDate));
                getContext().startActivity(intent);

                break;
            }
            case R.id.down_tv:{//有半小时的
                int row,column;//行，列 从1 开始算
                TextView tmpTv = (TextView)view;

                int num = Integer.parseInt(tmpTv.getText().toString());
                row = (num+6)/7;
                column = num-(row-1)*7;

                Date tapDate = DateUtil.getDateFromTap(beginDate, row, column, true);
                Intent intent = new Intent(getContext(), AddClassActivity.class);
                intent.putExtra("taped_date",DateUtil.dateToString(tapDate));
                getContext().startActivity(intent);

               break;
            }
        }
    }


}
