package com.example.liz.newstoday;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/5/6.
 */

public class QueryUtils {
    //构造器
    private QueryUtils(){

    }
    //json解析
    public static List<News> getContentFromJson(String jsonString){
        //判断json结果是否为空
        if (TextUtils.isEmpty(jsonString)){
            return null;
        }
        //初始化数组
        List<News> newsList = new ArrayList<>();
        //解析json数据
        try {
            JSONObject total = new JSONObject(jsonString);
            JSONArray articleList = total.getJSONArray("articles");
            for (int i=0;i<articleList.length();i++){
                JSONObject article = articleList.getJSONObject(i);
                String title = article.getString("title");
                String content = article.getString("description");
                News news = new News(title,content);
                newsList.add(news);
            }
        }catch (JSONException e){
            //待处理
        }
        Log.v("json",newsList.get(1).getContent());
        return newsList;
    }
    //url构成
    public static URL createUrl(String baseUrl){
        URL url = null;
        try{
            url = new URL(baseUrl);
        }catch (MalformedURLException e){
            //待处理
        }
        return url;
    }
    public static String getHttpResponse(URL url)throws IOException{
        String result = "";
        if (url == null){
            return result;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(10000 /* milliseconds */);
            httpURLConnection.setConnectTimeout(15000 /* milliseconds */);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            //如果http不是返回200的情况待处理
            inputStream = httpURLConnection.getInputStream();
            result = getResultFromInputStream(inputStream);
        }catch (IOException e){

        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return result;
    }
    public static String getResultFromInputStream(InputStream inputStream) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = bufferedReader.readLine();
            while(line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }
    //从string的url到数组
    public static List<News> getDataFromNet(String mUrl) {
        URL url = createUrl(mUrl);
        String newsJson = null;
        try {
            newsJson = getHttpResponse(url);
        }catch (IOException e){
            //待处理
        }
        List<News> dataList = getContentFromJson(newsJson);
        return dataList;
    }
}
