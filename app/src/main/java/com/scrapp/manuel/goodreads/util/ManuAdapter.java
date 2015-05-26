package com.scrapp.manuel.goodreads.util;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.scrapp.manuel.goodreads.R;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by Manuel on 22/05/2015.
 */
public class ManuAdapter extends RecyclerView.Adapter<ManuAdapter.MyViewHolder> {

    private List<Book> books = Collections.emptyList();

    public ManuAdapter(List<Book> books) {
        this.books = books;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_custom_item, parent, false);
        MyViewHolder myVH = new MyViewHolder(view);
        return myVH;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = books.get(position);
        Picasso.with(holder.imageView.getContext()).load(book.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void add(Book book) {
        books.add(book);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recyclerViewItemImage);
        }
    }
}
