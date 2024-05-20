package com.booki.ai.adapter;

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
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.R;
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

    @Override
    public void onBindViewHolder(@NonNull FontStyleAdapter.ViewHolder holder, int position) {


        holder.individual_font_style_tv.setText(""+arrayList.get(position).getFontName());





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView individual_font_style_tv;
        ImageView individual_font_style_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            individual_font_style_tv = itemView.findViewById(R.id.individual_font_style_tv);
            individual_font_style_iv = itemView.findViewById(R.id.individual_font_style_iv);

        }
    }
}
