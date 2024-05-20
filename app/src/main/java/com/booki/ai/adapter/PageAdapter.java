package com.booki.ai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.R;
import com.booki.ai.model.PageModel;

import java.util.ArrayList;

public class PageAdapter extends RecyclerView.Adapter<PageAdapter.ViewHolder> {

    Context context;
    ArrayList<PageModel> arrayList;

    public PageAdapter(Context coontext,ArrayList<PageModel> arrayList)
    {
        this.context=coontext;
        this.arrayList=arrayList;
    }

    @NonNull
    @Override
    public PageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_page,parent,false);
        return new PageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageAdapter.ViewHolder holder, int position) {
        holder.individual_page.setText(arrayList.get(position).getPageText());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView individual_page;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            individual_page = itemView.findViewById(R.id.individual_page);

        }
    }
}
