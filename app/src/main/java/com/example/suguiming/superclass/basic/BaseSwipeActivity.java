package com.example.suguiming.superclass.basic;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.suguiming.superclass.R;


public class BaseSwipeActivity extends AppCompatActivity{

    private DrawerLayout drawer;
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSwipeView();
        //---下面再做其他的---


    }

    private void initSwipeView(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_base_swipe);

        containerLayout = (LinearLayout) findViewById(R.id.container_layout);

        drawer = (DrawerLayout) findViewById(R.id.base_drawer_layout);
        drawer.openDrawer(GravityCompat.END);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                finish();
            }
        };
        drawer.setDrawerListener(toggle);
    }

    //子类要手势返回就用setMainView!!!!
    public void setMainView(int viewId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View tmpView = inflater.inflate(viewId, null);

        if (tmpView instanceof LinearLayout) {
            LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            tmpView.setLayoutParams(linearLayoutParams);
        } else if (tmpView instanceof RelativeLayout) {
            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            tmpView.setLayoutParams(relativeLayoutParams);
        }
        containerLayout.removeAllViews();
        containerLayout.addView(tmpView);
    }

    public void backViewTap(View v){}

    public void backImageTap(View v) {
        finish();
    }
}
