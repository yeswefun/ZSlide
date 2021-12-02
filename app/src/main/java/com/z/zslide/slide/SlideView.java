package com.z.zslide.slide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.z.zslide.R;

import java.lang.reflect.TypeVariable;

public class SlideView extends RelativeLayout {

    private SlideViewPager mSlideViewPager;
    private TextView mSlideViewText;
    private LinearLayout mSlideViewIndicatorWrap;

    private SlideViewAdapter mSlideViewAdapter;
    private final Context mContext;

    // 指示器状态
    private Drawable mIndicatorEnableDrawable;
    private Drawable mIndicatorDisableDrawable;

    private int mCurrentPosition = 0;

    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        inflate(context, R.layout.view_slide_layout, this);
        mSlideViewPager = findViewById(R.id.slide_view_pager);
        mSlideViewText = findViewById(R.id.slide_view_text);
        mSlideViewIndicatorWrap = findViewById(R.id.slide_view_indicator_wrap);

        mIndicatorEnableDrawable = new ColorDrawable(Color.RED);
        mIndicatorDisableDrawable = new ColorDrawable(Color.WHITE);
    }

    private void selectPage(int position) {
        // 将上一个点设置为禁用
        SlideViewIndicator oldView = (SlideViewIndicator) mSlideViewIndicatorWrap.getChildAt(mCurrentPosition);
        oldView.setDrawable(mIndicatorDisableDrawable);

        mCurrentPosition = position % mSlideViewAdapter.getCount();

        // 将当前点设置为启用
        SlideViewIndicator nowView = (SlideViewIndicator) mSlideViewIndicatorWrap.getChildAt(mCurrentPosition);
        nowView.setDrawable(mIndicatorEnableDrawable);

        // 设置轮播图文字
        mSlideViewText.setText(mSlideViewAdapter.getText(mCurrentPosition));
    }

    public void setAdapter(SlideViewAdapter adapter) {
        mSlideViewAdapter = adapter;
        mSlideViewPager.setAdapter(adapter);
        initIndicator();

        // 在初始化完mSlideViewAdapter后，我们才做初始化指示器和文本
        mSlideViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            /*
                当前选中的位置

                第一次进来，不会回调onPageSelected
             */
            @Override
            public void onPageSelected(int position) {
                selectPage(position);
            }
        });

        // 第一次进来，不会回调onPageSelected
        mSlideViewText.setText(mSlideViewAdapter.getText(0));
    }

    private void initIndicator() {

        // 设置indicatorWrap的位置
        mSlideViewIndicatorWrap.setGravity(Gravity.RIGHT);

        // 根据viewPager中的itemView的个数动态生成indicator
        int count = mSlideViewAdapter.getCount();
        for (int i = 0; i < count; i++) {
            SlideViewIndicator indicator = new SlideViewIndicator(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp2px(8), dp2px(8));
//            lp.leftMargin = lp.rightMargin = dp2px(2);
            lp.leftMargin = dp2px(4);
            indicator.setLayoutParams(lp);
            //indicator.setBackgroundColor(Color.RED);
            if (i == 0) {
                indicator.setDrawable(mIndicatorEnableDrawable);
            } else {
                indicator.setDrawable(mIndicatorDisableDrawable);
            }
            mSlideViewIndicatorWrap.addView(indicator);
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void startScroll() {
        mSlideViewPager.startScroll();
    }
}
