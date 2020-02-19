package com.example.thethirdapplication;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.thethirdapplication.models.Articles;
import com.example.thethirdapplication.models.MainResponse;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerHolder> {

    List<Articles> models;
    OnRecycleViewNewsListener onRecycleViewNewsListener;
    private int keyPosition;

    public RecyclerViewAdapter(List<Articles> models, OnRecycleViewNewsListener onRecycleViewNewsListener) {
        this.onRecycleViewNewsListener = onRecycleViewNewsListener;
        this.models = models;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row,null,false);
        RecyclerViewAdapter.RecyclerHolder viewHolder = new RecyclerViewAdapter.RecyclerHolder(view, onRecycleViewNewsListener);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RecyclerHolder holder, int position) {

        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView authorTextView;
        TextView titleTextView;
        TextView publishTextView;
        OnRecycleViewNewsListener onRecycleViewNewsListener;

        public RecyclerHolder(@NonNull View itemView, OnRecycleViewNewsListener onRecycleViewNewsListener) {
            super(itemView);
            authorTextView = itemView.findViewById(R.id.author);
            titleTextView = itemView.findViewById(R.id.title);
            publishTextView = itemView.findViewById(R.id.publishedAt);
            this.onRecycleViewNewsListener= onRecycleViewNewsListener;
            itemView.setOnClickListener(this);
        }

        void bind(int position) {
            keyPosition = position;
            authorTextView.setText(models.get(position).getAuthor());
            titleTextView.setText("||" + models.get(position).getTitle());
            publishTextView.setText("*" + models.get(position).getPublishedAt());
        }

        @Override
        public void onClick(View v) {
            Log.i("myTag", "xyi"+ authorTextView.getText() + "");
            onRecycleViewNewsListener.onNewsRecycleClick(keyPosition);

        }
    }

    public interface OnRecycleViewNewsListener {
        void onNewsRecycleClick(int key);
    }
}
