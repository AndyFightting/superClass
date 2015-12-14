package com.example.suguiming.superclass.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suguiming.superclass.R;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends Activity {

    ViewPager viewPager;
    List<View> viewList;

    TextView dot1;
    TextView dot2;
    TextView dot3;
    TextView dot4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager)findViewById(R.id.view_page);
        dot1 = (TextView)findViewById(R.id.dot1);
        dot2 = (TextView)findViewById(R.id.dot2);
        dot3 = (TextView)findViewById(R.id.dot3);
        dot4 = (TextView)findViewById(R.id.dot4);

        viewList =new ArrayList<>();
        LayoutInflater layoutInflater = this.getLayoutInflater();
        viewList.add(layoutInflater.inflate(R.layout.welcome0,null));
        viewList.add(layoutInflater.inflate(R.layout.welcome1,null));
        viewList.add(layoutInflater.inflate(R.layout.welcome2,null));
        viewList.add(layoutInflater.inflate(R.layout.welcome3, null));
        viewPager.setAdapter(new WelcomeAdapter(this, viewList));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    dot1.setBackgroundResource(R.drawable.circle_dot_blue);
                } else {
                    dot1.setBackgroundResource(R.drawable.circle_dot_white);
                }
                if (i == 1) {
                    dot2.setBackgroundResource(R.drawable.circle_dot_blue);
                } else {
                    dot2.setBackgroundResource(R.drawable.circle_dot_white);
                }
                if (i == 2) {
                    dot3.setBackgroundResource(R.drawable.circle_dot_blue);
                } else {
                    dot3.setBackgroundResource(R.drawable.circle_dot_white);
                }
                if (i == 3) {
                    dot4.setBackgroundResource(R.drawable.circle_dot_blue);
                } else {
                    dot4.setBackgroundResource(R.drawable.circle_dot_white);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}

        });
    }

    public void loginTap(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    //viewpager 适配器 内部类
    private class WelcomeAdapter extends PagerAdapter{

        Context ccontext;
        List<View> views;

        public WelcomeAdapter(Context context,List<View> views){
            this.ccontext = context;
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = views.get(position);
            container.removeView(view);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
            container.addView(views.get(position));
            return views.get(position);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

}
