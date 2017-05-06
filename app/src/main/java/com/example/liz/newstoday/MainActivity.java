package com.example.liz.newstoday;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private static final String NEWS_URL ="https://newsapi.org/v1/articles?source=abc-news-au&apiKey=5296ffe6512b4403ba7820df5a39c65f";
    private static final int NEWS_LOADER_ID = 1;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

    }
    public void iniRecyclerViews(List<News> data){
        newsAdapter = new NewsAdapter(MainActivity.this,data);
        recyclerView.setAdapter(newsAdapter);
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this,NEWS_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        iniRecyclerViews(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //data更新时的逻辑待处理

    }
}
