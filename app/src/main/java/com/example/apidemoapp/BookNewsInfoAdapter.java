package com.example.apidemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views

public class BookNewsInfoAdapter extends RecyclerView.Adapter<BookNewsInfoAdapter.MyViewHolder>{

    private List<BookNewsInfo> BookNewsInformation;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView AuthorOrPublisher, Title, Descr,PublishedDate;
        ImageView UrlToImage;

        public MyViewHolder(View view) {
            super(view);
            AuthorOrPublisher = (TextView) view.findViewById(R.id.AuthorOrPublisher);
            Title = (TextView) view.findViewById(R.id.Title);
            Descr = (TextView) view.findViewById(R.id.describ);
            PublishedDate = (TextView) view.findViewById(R.id.published);
            UrlToImage = (ImageView) view.findViewById(R.id.imageView);
        }
    }

    public BookNewsInfoAdapter(List<BookNewsInfo> BookNewsInfo) {
        this.BookNewsInformation = BookNewsInfo;
    }

    @NonNull
    @Override
    public BookNewsInfoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_row_layout, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookNewsInfoAdapter.MyViewHolder holder, int position) {

        BookNewsInfo bookNews = BookNewsInformation.get(position);
        holder.Title.setText("Title : "+bookNews.getTitle());
        holder.AuthorOrPublisher.setText("Publisher : "+bookNews.getAuthor());
        holder.PublishedDate.setText("Published Date : "+ bookNews.getPublishedAt());
        holder.Descr.setText("Details : "+ bookNews.getDescr());
        Picasso.get().load(bookNews.getUrlToImage()).into(holder.UrlToImage);
        animate(holder,context);

    }
    public void animate(RecyclerView.ViewHolder viewHolder, Context context) {
        final Animation animAnticipateOvershoot = AnimationUtils.loadAnimation(context, R.anim.bounce_interpolator);
        viewHolder.itemView.setAnimation(animAnticipateOvershoot);
    }

    @Override
    public int getItemCount() {
        return BookNewsInformation.size();
    }
}

