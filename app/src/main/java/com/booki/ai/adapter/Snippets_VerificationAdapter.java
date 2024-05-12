package com.booki.ai.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.R;
import com.booki.ai.activity.MainActivity;
import com.booki.ai.activity.SnippetVerificationAdminONLYActivity;
import com.booki.ai.model.InfiniteSnippetsModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Snippets_VerificationAdapter extends RecyclerView.Adapter<Snippets_VerificationAdapter.ViewHolder>{
    Context context;
    ArrayList<InfiniteSnippetsModel> arrayList;

    public Snippets_VerificationAdapter(Context context, ArrayList<InfiniteSnippetsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_snippet_for_verification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Set Data of Each Snippet
        holder.body.setText(arrayList.get(holder.getBindingAdapterPosition()).getBody());
        holder.body.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(holder.body.hasFocus()) {
                    SnippetVerificationAdminONLYActivity.setEdittext_message_inside_snippet(s.toString());
                    System.out.println("TEXT CHANGED");
                }
            }
        });
        /*
        Glide.with(context)
                .load(arrayList.get(holder.getAdapterPosition()).getImgSrc())
                .fitCenter()
                .into(holder.imageView);

         */

        ///////// REMOVE THIS IN THE FUTURE AND UNCOMMENT THE ABOVE COMMENT
        int random_image_num = ThreadLocalRandom.current().nextInt(1, 3);
        ArrayList<Integer> random_image = new ArrayList<Integer>();
        random_image.add(R.drawable.image1);
        random_image.add(R.drawable.image2);
        random_image.add(R.drawable.image3);

        Glide.with(context)
                .load(random_image.get(random_image_num))
                .fitCenter()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        EditText heading,body;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            body = itemView.findViewById(R.id.individual_snipper_verification_body);
            imageView = itemView.findViewById(R.id.individual_snipper_verification_imageview);

        }
    }
}
