package com.example.thethirdapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.thethirdapplication.models.MainResponse;
import com.example.thethirdapplication.retrofit.RetrofitInstance;
import com.example.thethirdapplication.retrofit.RetrofitInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;

public class NewsActivity extends AppCompatActivity {

    private int position;
    private int keyTheme;
    private RetrofitInterface retrofitInterface;
    private Call<MainResponse> listCall;
    private Context context = this;
    private TextView titleTextView;
    private TextView sourceTextView;
    private TextView descriptionTextView;
    private ImageView glideImageView;
    private ImageView picassoImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        titleTextView = findViewById(R.id.titleTextView);
        sourceTextView = findViewById(R.id.sourceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        glideImageView = findViewById(R.id.glideImageView);
        picassoImageView = findViewById(R.id.picassoImageView);
        position = getIntent().getIntExtra("key", 0);
        keyTheme = getIntent().getIntExtra("keyTheme", 0);

        retrofitInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
        switch(keyTheme){
            case 0:  listCall = retrofitInterface.getAllPhotos(NewsUtility.getSpecificDate(),NewsUtility.apiKey);
            break;
            case 1:  listCall = retrofitInterface.businessOfUsa(NewsUtility.apiKey);
            break;
            case 2:  listCall = retrofitInterface.getAllAppleNews(NewsUtility.apiKey);
            break;
            case 3:  listCall = retrofitInterface.techCrunch(NewsUtility.apiKey);
            break;
            case 4:  listCall = retrofitInterface.wallStreetJournal(NewsUtility.apiKey);
            break;
        }

        listCall.enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {

                Log.i("myTag", response.body().getArticles().get(position).getAuthor() + "");
                titleTextView.setText(response.body().getArticles().get(position).getTitle() + "");
                sourceTextView.setText(response.body().getArticles().get(position).getSource().getName() + "");
                descriptionTextView.setText(response.body().getArticles().get(position).getDescription() + "");
                Glide.with(context)
                        .load(response.body().getArticles().get(position).getUrlToImage())
                        .fitCenter()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(glideImageView);

                Picasso.with(context)
                        .load(response.body().getArticles().get(position).getUrlToImage())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(picassoImageView);
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.i("myTag", t + "");
            }

        });
    }
}
