package com.z.zslide.slide;

import android.view.View;

public abstract class SlideViewAdapter {
    /*
        根据位置获取viewPager中的itemView
     */
    public abstract View getView(int position);

    /*
        获取viewPager中itemView的个数
     */
    public abstract int getCount();

    /*
        根据位置获取轮播图当前图片对应的文本

        1. 文字在左, 指示器在右

        2. 指示器居中，没有文字

        不一定会有文字
     */
    public String getText(int position) {
        return "";
    }
}
