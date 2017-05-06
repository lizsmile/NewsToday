package com.example.liz.newstoday;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/5/6.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>  {
    List<News> newsList;
    Context context;
    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView contentText;
        View root;
        //疑问
        public NewsViewHolder(View v){
            super(v);
            titleText = (TextView)v.findViewById(R.id.title);
            contentText = (TextView)v.findViewById(R.id.article);
            root = v.findViewById(R.id.single_view);
        }

    }
    //构造器
    public NewsAdapter(Context contexts,List<News> list){
        context = contexts;
        newsList = list;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, null, false);
        NewsViewHolder vh = new NewsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        String ak = newsList.get(position).getTitle();
        Log.v("bind",ak);
        holder.titleText.setText(newsList.get(position).getTitle());
        holder.contentText.setText(newsList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
