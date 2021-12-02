package com.z.zslide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.z.zslide.slide.SlideView;
import com.z.zslide.slide.SlideViewAdapter;
import com.z.zslide.slide.SlideViewEntity;
import com.z.zslide.slide.SlideViewPager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*
ViewPager

    setAdapter
        populate - create/destroy itemView
            addNewItem
                instantiateItem - create itemView
                destroyItem     - destroy itemView

        不断创建与销毁itemView


    setOffscreenPageLimit
        // 1 x 3
        vp.setOffscreenPageLimit(1);

        // 1 2 x 4 5
        vp.setOffscreenPageLimit(2);

        // 1 2 3 x 5 6 7
        vp.setOffscreenPageLimit(3);


    setCurrentItem
        vp.setCurrentItem(0);
        vp.setCurrentItem(0, true|false);
            populate
            scrollToItem
                mScroller.startScroll() - animation


    onLayout() / onMessure() / onDraw()
 */
public class MainActivity extends AppCompatActivity {

    private SlideView mSlideView;
    private List<SlideViewEntity> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    /*
        模拟加载网络数据
     */
    private void initData() {
        imageList = new ArrayList<>();
        imageList.add(new SlideViewEntity(0, "标题-0", "http://192.168.1.102/img/mobile/slide/10.jpg", "route-0"));
        imageList.add(new SlideViewEntity(1, "标题-1", "http://192.168.1.102/img/mobile/slide/11.jpg", "route-1"));
        imageList.add(new SlideViewEntity(2, "标题-2", "http://192.168.1.102/img/mobile/slide/12.jpg", "route-2"));
    }

    private void initView() {

        mSlideView = findViewById(R.id.slide_view);

        mSlideView.setAdapter(new SlideViewAdapter() {
            @Override
            public View getView(int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                String imagePath = imageList.get(position % imageList.size()).path;
                Glide.with(MainActivity.this)
                        .load(imagePath)
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageView);
                return imageView;
            }

            @Override
            public int getCount() {
                return imageList.size();
            }

            @Override
            public String getText(int position) {
                return imageList.get(position % imageList.size()).text;
            }
        });

        mSlideView.startScroll();
    }

    /*
        测试页面销毁，handle是否被回收
     */
    public void handleTest(View view) {
        startActivity(new Intent(this, TestActivity.class));
    }
}