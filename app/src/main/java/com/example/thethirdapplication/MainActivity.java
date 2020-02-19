package com.example.thethirdapplication;

import com.example.thethirdapplication.models.*;
import com.example.thethirdapplication.retrofit.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, SwipeRefreshLayout.OnRefreshListener, RecyclerViewAdapter.OnRecycleViewNewsListener {
    private RecyclerView rvMain;
    private Call<MainResponse> listCall;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RetrofitInterface retrofitInterface;
    private View mErrorView;
    private List listThemeAdapter;
    private Spinner spinnerTheme;
    private List<String> themesNewsList;
    private String bitcoin = "Bitcoin";
    private String businnesOfUs = "Business of USA";
    private String apple = "Apple";
    private String TechCrunch = "TechCrunch";
    private String wallStreetJournal = "Wall Street Journal";
    int keyTheme = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mErrorView = findViewById(R.id.error_view);
            initRecycleView();
            NewsUtility.getSpecificDate();
            initViewSwipeToRefresh();
            fillAdapter();
            spinnerTheme.setOnItemSelectedListener(this);

            onRefresh();
        } catch (Exception e) {
            showError();
        }
    }

    private void parseData(List<Articles> body) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(body, this);
        rvMain.setAdapter(recyclerViewAdapter);
    }

    private void initViewSwipeToRefresh() {
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    public void initRecycleView() {
        rvMain = findViewById(R.id.rvMain);
        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public void onRefresh() {
        showData();
        retrofitInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
        if (keyTheme == 0) {
            listCall = retrofitInterface.getAllPhotos(NewsUtility.getSpecificDate());
        } else if (keyTheme == 1) {
            listCall = retrofitInterface.businessOfUsa();
        } else if (keyTheme == 2) {
            listCall = retrofitInterface.getAllAppleNews();
        } else if (keyTheme == 3) {
            listCall = retrofitInterface.techCrunch();
        } else if (keyTheme == 4) {
            listCall = retrofitInterface.wallStreetJournal();
        }
        listCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                Log.i("myTag", response.raw() + "");
                parseData(response.body().getArticles());

            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.i("myTag", t + "");
                showError();
            }

        });
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    public void fillAdapter() {
        spinnerTheme = findViewById(R.id.spinnerTheme);

        themesNewsList = new ArrayList<String>();
        themesNewsList.add("Bitcoin");
        themesNewsList.add("Business of USA");
        themesNewsList.add("Apple");
        themesNewsList.add("TechCrunch");
        themesNewsList.add("Wall Street Journal");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, themesNewsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTheme.setAdapter(adapter);
    }

    private void showData() {
        mErrorView.setVisibility(View.GONE);
        rvMain.setVisibility(View.VISIBLE);

    }

    private void showError() {
        rvMain.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewsRecycleClick(int key) {
        Intent intent = new Intent(this, NewsActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("keyTheme", keyTheme);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spinnerTheme.getSelectedItem().toString().equals(businnesOfUs)) {
            keyTheme = 1;
            onRefresh();
        } else if (spinnerTheme.getSelectedItem().toString().equals(bitcoin)) {
            keyTheme = 0;
            onRefresh();
        } else if (spinnerTheme.getSelectedItem().toString().equals(apple)) {
            keyTheme = 2;
            onRefresh();
        } else if (spinnerTheme.getSelectedItem().toString().equals(TechCrunch)) {
            keyTheme = 3;
            onRefresh();
        } else if (spinnerTheme.getSelectedItem().toString().equals(wallStreetJournal)) {
            keyTheme = 4;
            onRefresh();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Nothing TODO
    }
}
