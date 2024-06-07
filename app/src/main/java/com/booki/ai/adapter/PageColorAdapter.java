package com.booki.ai.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.booki.ai.IndividualPageActivity;
import com.booki.ai.R;
import com.booki.ai.activity.BookReadActivity;
import com.booki.ai.fragment.CustomiseTextBottomSheet;
import com.booki.ai.model.PageColorModel;

import java.util.ArrayList;

public class PageColorAdapter extends RecyclerView.Adapter<PageColorAdapter.ViewHolder> {

    Context context;
    ArrayList<PageColorModel> arrayList;
    WebView book_read_main_tv;
    TextView book_read_page_no;
    BookReadActivity bookReadActivity;
    public PageColorAdapter(Context context, ArrayList<PageColorModel> arrayList, WebView book_read_main_tv, TextView book_read_page_no, BookReadActivity bookReadActivity)
    {
        this.context=context;
        this.arrayList=arrayList;
        this.book_read_main_tv=book_read_main_tv;
        this.book_read_page_no=book_read_page_no;
        this.bookReadActivity=bookReadActivity;
    }

    @NonNull
    @Override
    public PageColorAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.individual_page_color,parent,false);
        return new PageColorAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PageColorAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.individual_page_color_cv.setCardBackgroundColor(arrayList.get(position).getPageColor());
//        holder.individual_page_color_cv.setRadius(500);

        if(bookReadActivity.pageColorPosition==position)
            holder.individual_page_color_iv.setBackgroundResource(R.drawable.page_color_border);
        else
            holder.individual_page_color_iv.setBackgroundResource(R.color.transparent);



        holder.individual_page_color_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bookReadActivity.pageColorPosition = position;
                notifyDataSetChanged();

                book_read_main_tv.setBackgroundColor(arrayList.get(position).getPageColor());

                CustomiseTextBottomSheet.textColor = arrayList.get(position).getTextColor();

                BookReadActivity.htmlStyle = "<font color='"+CustomiseTextBottomSheet.textColor+"' size="+CustomiseTextBottomSheet.textSize+">";

                System.out.println(BookReadActivity.webData);
                BookReadActivity.loadData();

//                book_read_main_tv.loadDataWithBaseURL(null, bookReadActivity.webData,"text/html", "UTF-8",null);


//                book_read_main_tv.setTextColor(arrayList.get(position).getTextColor());


//                IndividualPageActivity.changeColor(arrayList.get(position).getPageColor(), arrayList.get(position).getTextColor());


                book_read_page_no.setBackgroundColor(arrayList.get(position).getPageColor());
                book_read_page_no.setTextColor(Color.parseColor(arrayList.get(position).getTextColor()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView individual_page_color_cv;
        ImageView individual_page_color_iv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            individual_page_color_cv = itemView.findViewById(R.id.individual_page_color_cv);
            individual_page_color_iv = itemView.findViewById(R.id.individual_page_color_iv);

        }

}

}