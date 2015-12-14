package com.example.suguiming.superclass.classTab.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by suguiming on 15/10/27.
 */
public class SGMScrollView extends ScrollView {

    public ScrollingListener scrollingListener;

    public ScrollingListener getScrollingListener() {
        return scrollingListener;
    }

    public void setScrollingListener(ScrollingListener scrollingListener) {
        this.scrollingListener = scrollingListener;
    }

    public SGMScrollView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(scrollingListener != null){
            scrollingListener.onScrolling( l, t, oldl, oldt);
        }
    }

   public interface ScrollingListener{
      void onScrolling(int l, int t, int oldl, int oldt);
   }

}
