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

import com.booki.ai.activity.Book_MarketDisplayActivity;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.booki.ai.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
        holder.body.setText(arrayList.get(holder.getAdapterPosition()).getBody());
        holder.authorname.setText(arrayList.get(holder.getAdapterPosition()).getBookauthor());
        holder.bookname.setText(arrayList.get(holder.getAdapterPosition()).getBookname());

        holder.parent_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Book_MarketDisplayActivity.class);
                intent.putExtra("book_id", arrayList.get(holder.getAdapterPosition()).getBookid());
                context.startActivity(intent);
                ((Activity) context).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        Glide.with(context)
                .load(arrayList.get(holder.getAdapterPosition()).getImgSrc())
                .fitCenter()
                .into(holder.imageView);


//        ///////// REMOVE THIS IN THE FUTURE AND UNCOMMENT THE ABOVE COMMENT
//        int random_image_num = ThreadLocalRandom.current().nextInt(0, 3);
//        ArrayList<Integer> random_image = new ArrayList<Integer>();
//        random_image.add(R.drawable.image1);
//        random_image.add(R.drawable.image2);
//        random_image.add(R.drawable.image3);
//
//        Glide.with(context)
//                .load(random_image.get(random_image_num))
//                .fitCenter()
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView body, bookname, authorname;
        ImageView imageView;
        CardView parent_card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookname = itemView.findViewById(R.id.individual_snippet_bookname);
            authorname = itemView.findViewById(R.id.individual_snippet_authorname);
            body = itemView.findViewById(R.id.individual_snippet_body);
            parent_card = itemView.findViewById(R.id.individual_snippet_parent_card);
            imageView = itemView.findViewById(R.id.individual_snippet_imageview);

        }
    }
}