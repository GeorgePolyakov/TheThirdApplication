package com.example.thethirdapplication;


import com.example.thethirdapplication.models.MainResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitInterface {
  @GET("/v2/everything?q=bitcoin&from=currentDate&sortBy=publishedAt&apiKey=4d02332f1b864beda94d60580952d46a")
  Call<MainResponse> getAllPhotos(@Query("currentDate") String currentDate);
}
