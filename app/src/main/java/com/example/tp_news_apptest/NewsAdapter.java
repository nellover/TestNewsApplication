package com.example.tp_news_apptest;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tp_news_apptest.NewsArticles.Article;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private Context context;
    private List<Article> articles;
    private AdapterView.OnItemClickListener onItemClickListener;

    public NewsAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {

        Article article = articles.get(position);


        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());


        Glide.with(context)
                .load(article.getUrlToImage())
                .placeholder(R.drawable.placeholder_image)
                .into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("title", article.getTitle());
            intent.putExtra("description", article.getDescription());
            intent.putExtra("imageUrl", article.getUrlToImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public interface OnItemClickListener {
        void onItemClick(Article article);
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, descriptionTextView;
        ShapeableImageView imageView;
        private TextView title;
        public NewsViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            descriptionTextView = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
        }

        public void bind(Article article, OnItemClickListener listener) {
            title.setText(article.getTitle());
            itemView.setOnClickListener(v -> listener.onItemClick(article));
        }
    }
}

