package com.z.zslide.slide;

/*
    轮播图的实体类
 */
public class SlideViewEntity {

    public int id;
    public String text;    // 图片标题
    public String path;    // 图片路径
    public String link;    // 图片路由

    public SlideViewEntity() {
    }

    /*
        id
        text: 图片描述
        path: 图片路径
        link: 点击图片跳转到哪里
     */
    public SlideViewEntity(int id, String text, String path, String link) {
        this.id = id;
        this.text = text;
        this.path = path;
        this.link = link;
    }
}
