package com.example.thethirdapplication.retrofit;


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

  @GET("/v2/top-headlines?country=us&category=business&apiKey=4d02332f1b864beda94d60580952d46a")
  Call<MainResponse> businessOfUsa ();

  @GET("/v2/everything?q=apple&from=2020-02-18&to=2020-02-18&sortBy=popularity&apiKey=4d02332f1b864beda94d60580952d46a")
  Call<MainResponse> getAllAppleNews ();

  @GET("/v2/top-headlines?sources=techcrunch&apiKey=4d02332f1b864beda94d60580952d46a")
  Call<MainResponse> techCrunch ();

  @GET("/v2/everything?domains=wsj.com&apiKey=4d02332f1b864beda94d60580952d46a")
  Call<MainResponse> wallStreetJournal();
}
