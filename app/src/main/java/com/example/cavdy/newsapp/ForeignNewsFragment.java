package com.example.cavdy.newsapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.service.autofill.FillEventHistory;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cavdy.newsapp.Adapter.ForeignnewsHorizontalRecyclerviewAdapter;
import com.example.cavdy.newsapp.Adapter.ForeignnewsRecyclerviewAdapter;

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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForeignNewsFragment extends Fragment {

    private static final String USGS_REQUEST_URL = "https://content.guardianapis.com/search?q=debate%20AND%20economy&tag=politics/politics&from-date=2014-01-01&api-key=1b4996a8-fa54-4f58-b7ac-2f68cd476e54";
    @BindView(R.id.horizontal_recyclerview)
    RecyclerView horizontalRecyclerView;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private ForeignnewsHorizontalRecyclerviewAdapter foreignnewsHorizontalRecyclerviewAdapter;
    private ForeignnewsRecyclerviewAdapter foreignnewsRecyclerviewAdapter;

    public static final String LOG_TAG = ForeignNewsFragment.class.getName();

    public ForeignNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_foreign_news, container, false);
        ButterKnife.bind(this, view);

        foreignnewsHorizontalRecyclerviewAdapter = new ForeignnewsHorizontalRecyclerviewAdapter(getActivity(), new ArrayList<News>());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(layoutManager);
        horizontalRecyclerView.setAdapter(foreignnewsHorizontalRecyclerviewAdapter);

        foreignnewsRecyclerviewAdapter = new ForeignnewsRecyclerviewAdapter(getActivity(), new ArrayList<News>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(foreignnewsRecyclerviewAdapter);

        NewsAsyncTask newsAsyncTask = new NewsAsyncTask();
        newsAsyncTask.execute(USGS_REQUEST_URL);

        return view;
    }

    private class NewsAsyncTask extends AsyncTask<String, Void, List<News>> {

        @Override
        protected List<News> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<News> result = QueryUtils.fetchNewsData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<News> news) {
            super.onPostExecute(news);
        }
    }

}
