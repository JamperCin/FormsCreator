package com.kode.formscreatorlib.Utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomViewPager extends ViewPager {

    /**add a boolean function to either enable or disable swiping viewpager by hand horizontally**/
    private boolean enabled;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;/**by default set it to true**/
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /**if enabled then allow touch events on the viewpager**/
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        /**if enabled then allow swiping of the viewpager**/
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }


    /**set the boolean to true/false to either allow/disallow paging respectivelly**/
    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}