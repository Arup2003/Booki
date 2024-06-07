package com.booki.ai.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.R;
import com.booki.ai.activity.BookReadActivity;
import com.booki.ai.activity.Book_MarketDisplayActivity;
import com.booki.ai.model.FontStyleModel;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class FontStyleAdapter extends RecyclerView.Adapter<FontStyleAdapter.ViewHolder> {

    Context context;
    ArrayList<FontStyleModel> arrayList;

    public FontStyleAdapter(Context context, ArrayList<FontStyleModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FontStyleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_font_style, parent, false);
        return new FontStyleAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FontStyleAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.individual_font_style_tv.setText(""+arrayList.get(position).getFontName());

        if(BookReadActivity.fontStylePosition==position)
            holder.individual_font_style_card.setBackgroundResource(R.drawable.font_style_selected);
        else
            holder.individual_font_style_card.setBackgroundResource(R.color.transparent);



        holder.individual_font_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookReadActivity.fontStylePosition=position;
                notifyDataSetChanged();

                BookReadActivity.head = "<html><head><style>body { text-align: center; font-family: "+arrayList.get(position).getFontName()+"}</style></head>";
                BookReadActivity.loadData();
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView individual_font_style_tv;
        ImageView individual_font_style_iv;
        ConstraintLayout individual_font_style;
        CardView individual_font_style_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            individual_font_style_tv = itemView.findViewById(R.id.individual_font_style_tv);
            individual_font_style_iv = itemView.findViewById(R.id.individual_font_style_iv);
            individual_font_style = itemView.findViewById(R.id.individual_font_style);
            individual_font_style_card = itemView.findViewById(R.id.individual_font_style_card);
        }
    }
}
