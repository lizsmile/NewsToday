package com.example.liz.newstoday;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    private static final String NEWS_URL ="https://newsapi.org/v1/articles?";
    private static final String NEWS_KEY ="&apiKey=5296ffe6512b4403ba7820df5a39c65f";
    private static final int NEWS_LOADER_ID = 1;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    RecyclerView.LayoutManager layoutManager;
    //drawer
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;
    String urlEach  = "abc-news-au";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //drawer layout
        mPlanetTitles = getResources().getStringArray(R.array.planets_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_list_item, mPlanetTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

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
        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("source",urlEach);
        Log.v("url",uriBuilder.toString());

        return new NewsLoader(MainActivity.this,uriBuilder.toString()+NEWS_KEY);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        iniRecyclerViews(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        //data更新时的逻辑待处理

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
    //点击drawer里面的选项
    private void selectItem(int position) {
        //url改变，视图重新加载。
        //首先根据array生成url参数
        String former = getResources().getStringArray(R.array.planets_array)[position];
        String[] urlPart = former.toLowerCase().split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<urlPart.length;i++){
            builder.append(urlPart[i]);
            builder.append("-");
        }
        String tempF = builder.toString();
        String temp = tempF.replace("(","").replace(")","");
        urlEach = temp.substring(0,temp.length()-1);
        Toast.makeText(this,urlEach,Toast.LENGTH_SHORT).show();
        //生成新的url
        MainActivity.this.getLoaderManager().restartLoader(NEWS_LOADER_ID,null,this);


        // update selected item and title, then close the drawer
        mDrawerList.setItemChecked(position, true);
//        setTitle(mPlanetTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
    }
}
