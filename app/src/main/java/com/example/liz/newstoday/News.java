package com.example.liz.newstoday;

/**
 * Created by lenovo on 2017/5/6.
 */

public class News {
    private String title;
    private String content;
    public News(String titles,String contents){
        title = titles;
        content = contents;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
