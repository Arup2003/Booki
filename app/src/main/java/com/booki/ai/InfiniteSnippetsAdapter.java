package com.booki.ai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class InfiniteSnippetsAdapter extends RecyclerView.Adapter<InfiniteSnippetsAdapter.ViewHolder>{
    Context context;
    ArrayList<InfiniteSnippetsModel> arrayList;

    public InfiniteSnippetsAdapter(Context context, ArrayList<InfiniteSnippetsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_snippet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set Data of Each Snippet
        holder.heading.setText(arrayList.get(holder.getAdapterPosition()).getHeading());
        holder.body.setText(arrayList.get(holder.getAdapterPosition()).getBody());
        holder.imageView.setImageResource(arrayList.get(holder.getAdapterPosition()).getImage_src());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView heading,body;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading = itemView.findViewById(R.id.individual_snippet_heading);
            body = itemView.findViewById(R.id.individual_snippet_body);
            imageView = itemView.findViewById(R.id.individual_snippet_imageview);

        }
    }
}