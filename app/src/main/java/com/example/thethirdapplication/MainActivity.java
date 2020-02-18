package com.example.thethirdapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.thethirdapplication.models.*;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rvMain;
    private Calendar c;
    private String currentDate;
    private Call<MainResponse> listCall;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RetrofitInterface retrofitInterface;
    private View mErrorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mErrorView = findViewById(R.id.error_view);
            initRecycleView();
            getSpecificDate();
            initViewSwipeToRefresh();
            retrofitInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
            onRefresh();
        } catch (Exception e) {
            showError();
        }

        Log.i("myTag", retrofitInterface.getAllPhotos(currentDate).toString());
    }

    private void parseData(List<Articles> body) {
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(body);
        rvMain.setAdapter(recyclerViewAdapter);
    }

    private void getSpecificDate() {
        c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        currentDate = df.format(date);
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
        listCall = retrofitInterface.getAllPhotos(currentDate);
        listCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
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

    private void showData(){
        mErrorView.setVisibility(View.GONE);
        rvMain.setVisibility(View.VISIBLE);

    }

    private void showError() {
        rvMain.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

}
