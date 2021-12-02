package com.z.zslide.slide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/*
    指示器
 */
public class SlideViewIndicator extends View {

    private Drawable mDrawable;

    public SlideViewIndicator(Context context) {
        this(context, null);
    }

    public SlideViewIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideViewIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        // 间接调用view的onDraw
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
            mDrawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            mDrawable.draw(canvas);
        }
    }
}
