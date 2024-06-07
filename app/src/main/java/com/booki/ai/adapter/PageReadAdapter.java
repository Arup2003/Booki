package com.booki.ai.adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.IndividualPageActivity;
import com.booki.ai.R;

import java.util.List;

public class PageReadAdapter extends RecyclerView.Adapter<PageReadAdapter.PageViewHolder> {
    private List<String> pages;
    ConstraintLayout book_read_top_panel;
    CardView book_read_pill_panel;
    Context context;

    int backgroundColor = Color.parseColor("#ffffff");
    int textColor = Color.parseColor("#000000");

    public PageReadAdapter(List<String> pages, ConstraintLayout book_read_top_panel, CardView book_read_pill_panel, Context context)
    {
        this.pages = pages;
        this.context=context;
        this.book_read_pill_panel=book_read_pill_panel;
        this.book_read_top_panel=book_read_top_panel;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_page, parent, false);
        return new PageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        holder.individual_page.setText(pages.get(position));

        holder.individual_page.setTextColor(textColor);
        holder.individual_page.setBackgroundColor(backgroundColor);

        holder.individual_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int visibility = book_read_top_panel.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE;

                book_read_top_panel.setVisibility(visibility);
                book_read_pill_panel.setVisibility(visibility);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    static class PageViewHolder extends RecyclerView.ViewHolder {
        TextView individual_page;

        PageViewHolder(View view) {
            super(view);
            individual_page = view.findViewById(R.id.individual_page);

        }
    }

    public static void changePageStyle(int backgroundColor, int textColor){
//        this.backgroundColor=backgroundColor;
//        this.textColor =textColor;
    }
}

