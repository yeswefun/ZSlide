package com.z.zslide.slide;

import android.content.Context;
import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Scroller;

public class SlideViewScroller extends Scroller {

    // 动画持续时间
    private int mDuration = 720;

    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    public SlideViewScroller(Context context) {
//        this(context, new DecelerateInterpolator());
//        this(context, new BounceInterpolator());
        this(context, null);
    }

    public SlideViewScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public SlideViewScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        Log.e("TAG", "startScroll: " + mDuration);
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
