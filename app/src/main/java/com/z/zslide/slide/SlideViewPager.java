package com.z.zslide.slide;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class SlideViewPager extends ViewPager {

    // 自定义adapter
    private SlideViewAdapter mAdapter;

    private SlideViewScroller mScroller;

    public SlideViewPager(@NonNull Context context) {
        super(context);
    }

    public SlideViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAnimationScroller(context);
    }

    // 改变动画速度
    private void initAnimationScroller(@NonNull Context context) {
        mScroller = new SlideViewScroller(context);
        try {
            Field fieldScroller = ViewPager.class.getDeclaredField("mScroller");
            fieldScroller.setAccessible(true);
            fieldScroller.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAnimationDuration(int duration) {
        mScroller.setDuration(duration);
    }


    public void setAdapter(@Nullable SlideViewAdapter adapter) {

        // 设置自定义adapter
        mAdapter = adapter;

        // 设置父类ViewPager中的adapter
        setAdapter(new SlideViewPagerAdapter());
    }

    private class SlideViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            // 无限轮播
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        /*
            创建itemView

            adapter设计模式 - 让用户自定义view

            container == ViewPager
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = mAdapter.getView(position);
            container.addView(itemView);
            return itemView;
        }

        /*
            销毁itemView
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

    // ===================== 实现自动轮播 =====================
    public static final int MSG_SCROLL = 0x0;
    public static final int MSG_DELAY = 3500;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_SCROLL:
                    setCurrentItem(getCurrentItem() + 1);
                    startScroll();
                    Log.e("TAG", "scroll");
                    break;
            }
        }
    };

    public void startScroll() {
        // 清除之前的消息
        mHandler.removeMessages(MSG_SCROLL);
        mHandler.sendEmptyMessageDelayed(MSG_SCROLL, MSG_DELAY);
    }

    /*
     * Activity#onDestroy -> ViewPager#onDetachedFromWindow
     *
     * TODO: seems not work!
     */
    @Override
    protected void onDetachedFromWindow() {
        Log.e("TAG", "onDetachedFromWindow");
        super.onDetachedFromWindow();
        // 解决不了呀
        mHandler.removeMessages(MSG_SCROLL);
        mHandler = null;
    }
}
