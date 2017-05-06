package com.example.liz.newstoday;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by lenovo on 2017/5/6.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {
    String newsUrl;
    public NewsLoader(Context context,String url){
        super(context);
        newsUrl = url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (newsUrl == null){
            return null;
        }
        List<News> newsList = QueryUtils.getDataFromNet(newsUrl);
        return newsList;
    }
}
