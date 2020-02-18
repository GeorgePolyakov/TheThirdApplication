package com.example.thethirdapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thethirdapplication.models.Articles;
import com.example.thethirdapplication.models.MainResponse;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {
    List<Articles> models;

    public RecyclerViewAdapter(List<Articles> models) {
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,null,false);
        return  new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.authorTextView.setText(models.get(position).getAuthor());
        holder.titleTextView.setText("||" + models.get(position).getTitle());
        holder.publishTextView.setText("*" + models.get(position).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView authorTextView;
        TextView titleTextView;
        TextView publishTextView;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.author);
            titleTextView = itemView.findViewById(R.id.title);
            publishTextView = itemView.findViewById(R.id.publishedAt);

        }
    }
}
