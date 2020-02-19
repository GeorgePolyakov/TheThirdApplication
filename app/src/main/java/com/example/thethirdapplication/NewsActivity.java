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
import java.util.Calendar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class NewsActivity extends AppCompatActivity {

    private RetrofitInterface retrofitInterface;
    private Call<MainResponse> listCall;
    private Calendar c;
    private String currentDate;
    private Context context=this;
    private TextView titleTextView;
    private TextView sourceTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private ImageView imageView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        titleTextView = findViewById(R.id.titleTextView);
        sourceTextView = findViewById(R.id.sourceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        imageView = findViewById(R.id.glideImageView);
        imageView2 = findViewById(R.id.picassoImageView);

        final int position = getIntent().getIntExtra("key",0);
        final int keyTheme = getIntent().getIntExtra("keyTheme",0);

        retrofitInterface = RetrofitInstance.getRetrofitInstance().create(RetrofitInterface.class);
        if (keyTheme == 0) {
            listCall = retrofitInterface.getAllPhotos(NewsUtility.getSpecificDate());
        } else if (keyTheme == 1) {
            listCall = retrofitInterface.businessOfUsa();
        } else if (keyTheme == 2){
            listCall = retrofitInterface.getAllAppleNews();
        }
        else if (keyTheme == 3){
            listCall = retrofitInterface.techCrunch();
        }
        else if (keyTheme == 4){
            listCall = retrofitInterface.wallStreetJournal();
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
                        .into(imageView);

                Picasso.with(context)
                        .load(response.body().getArticles().get(position).getUrlToImage())
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(imageView2);
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                Log.i("myTag", t + "");
               // showError();
            }

        });
    }
}
