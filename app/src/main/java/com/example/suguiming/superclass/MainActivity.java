package com.example.suguiming.superclass;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.suguiming.superclass.basic.BaseSwipeActivity;
import com.example.suguiming.superclass.classTab.ClassFragment;
import com.example.suguiming.superclass.countTab.CountFragment;
import com.example.suguiming.superclass.meTab.MeFragment;
import com.example.suguiming.superclass.studentTab.StudentListFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseSwipeActivity {

    private CountFragment countFragment;
    private ClassFragment classFragment;
    private StudentListFragment studentFragment;
    private MeFragment meFragment;

    private Fragment currentFragment;
    private List<ImageView> tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTabImage();
        initFragment();
        selectFragment(0);
    }

    private void initTabImage(){
        tabs = new ArrayList<>();
        tabs.add((ImageView) findViewById(R.id.tab_count_img));
        tabs.add((ImageView) findViewById(R.id.tab_class_img));
        tabs.add((ImageView) findViewById(R.id.tab_student_img));
        tabs.add((ImageView) findViewById(R.id.tab_me_img));
    }

    private void initFragment(){
        countFragment = (CountFragment)getFragmentManager().findFragmentById(R.id.count_fg);
        classFragment = (ClassFragment)getFragmentManager().findFragmentById(R.id.class_fg);
        studentFragment = (StudentListFragment)getFragmentManager().findFragmentById(R.id.student_fg);
        meFragment = (MeFragment)getFragmentManager().findFragmentById(R.id.me_fg);

        countFragment.mainActivity = this;
        classFragment.mainActivity = this;
        studentFragment.mainActivity = this;
        meFragment.mainActivity = this;

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.hide(countFragment);
        transaction.hide(classFragment);
        transaction.hide(studentFragment);
        transaction.hide(meFragment);
        transaction.commit();
    }

    private void selectFragment(int index){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (currentFragment != null){
            transaction.hide(currentFragment);
        }

        switch (index)
        {
            case 0:
                transaction.show(countFragment);
                currentFragment = countFragment;
                countFragment.tabClicked();
                break;
            case 1:
                transaction.show(classFragment);
                currentFragment = classFragment;
                classFragment.tabClicked();
                break;

            case 2:
                transaction.show(studentFragment);
                currentFragment = studentFragment;
                studentFragment.tabClicked();
                break;
            case 3:
                transaction.show(meFragment);
                currentFragment = meFragment;
                meFragment.tabClicked();
                break;
        }
        transaction.commit();
        changeTabImages(index);
    }

    private void changeTabImages(int index){
        tabs.get(0).setImageResource(R.mipmap.tab1);
        tabs.get(1).setImageResource(R.mipmap.tab2);
        tabs.get(2).setImageResource(R.mipmap.tab3);
        tabs.get(3).setImageResource(R.mipmap.tab4);

        switch(index){
            case 0:{
                tabs.get(index).setImageResource(R.mipmap.tab1s);
                break;
            }
            case 1:{
                tabs.get(index).setImageResource(R.mipmap.tab2s);
                break;
            } case 2:{
                tabs.get(index).setImageResource(R.mipmap.tab3s);
                break;
            } case 3:{
                tabs.get(index).setImageResource(R.mipmap.tab4s);
                break;
            }
        }
    }

    public void tabTaped(View view){
        switch (view.getId()){
            case R.id.tab_count_layout:{
                selectFragment(0);
                break;
            }
            case R.id.tab_class_layout:{
                selectFragment(1);
                break;
            }
            case R.id.tab_student_layout:{
                selectFragment(2);
                break;
            }
            case R.id.tab_me_layout:{
                selectFragment(3);
                break;
            }
        }
    }
}
