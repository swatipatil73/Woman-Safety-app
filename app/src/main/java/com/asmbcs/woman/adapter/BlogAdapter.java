package com.asmbcs.woman.adapter;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.asmbcs.woman.BlogDetailActivity;
import com.asmbcs.woman.R;
import com.asmbcs.woman.modelclass.Blog;
import com.bumptech.glide.Glide;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.BlogViewHolder> {

    private Context context;
    private List<Blog> blogList;

    public BlogAdapter(Context context, List<Blog> blogList) {
        this.context = context;
        this.blogList = blogList;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blog_item, parent, false);
        return new BlogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {
        Blog blog = blogList.get(position);

        holder.title.setText(blog.getTitle());

        // Load image using Glide
        Glide.with(context)
                .load(blog.getImageUrl())
                .placeholder(R.drawable.women_helpline) // Placeholder image
                .into(holder.imageView);

        // Handle view button click
        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, BlogDetailActivity.class);
            intent.putExtra("title", blog.getTitle());
            intent.putExtra("imageUrl", blog.getImageUrl());
            intent.putExtra("description", blog.getDescription());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return blogList.size();
    }

    public void updateList(List<Blog> newList) {
        blogList = newList;
        notifyDataSetChanged();
    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        Button viewButton;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.blogImage);
            title = itemView.findViewById(R.id.blogTitle);
            viewButton = itemView.findViewById(R.id.viewButton);
        }
    }
}
