package com.example.android.newsroom;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";

    private NewsAdapter adapter;

    private TextView mEmptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateView = findViewById(R.id.empty_view);

        final ListView newsListView = findViewById(R.id.list);

        newsListView.setEmptyView(mEmptyStateView);

        adapter = new NewsAdapter(this, new ArrayList<News>());

        newsListView.setAdapter(adapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currNews = adapter.getItem(position);

                Uri newsUri = Uri.parse(currNews.getUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(intent);
            }
        });


    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", "debates");
        uriBuilder.appendQueryParameter("api-key", "test");

        uriBuilder.appendQueryParameter("format", "json");
        uriBuilder.appendQueryParameter("order-by", "relevance");

        return  new NewsLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        adapter.clear();
        mEmptyStateView.setText("No News found");
        if(data!=null&&!data.isEmpty())
            adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
