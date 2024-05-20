package com.booki.ai.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.booki.ai.activity.BookReadActivity;
import com.booki.ai.adapter.FontStyleAdapter;
import com.booki.ai.adapter.InfiniteSnippetsAdapter;
import com.booki.ai.adapter.PageColorAdapter;
import com.booki.ai.model.FontStyleModel;
import com.booki.ai.model.PageColorModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import com.booki.ai.R;

import java.util.ArrayList;

public class CustomiseTextBottomSheet extends BottomSheetDialog {

    BookReadActivity bookReadActivity;
    RecyclerView book_read_font_style_rv;
    RecyclerView book_read_font_color_rv;
    ArrayList<FontStyleModel> fontStyles;
    ArrayList<PageColorModel> pageColors;
    FontStyleAdapter fontStyleAdapter;
    PageColorAdapter pageColorAdapter;
    TextView book_read_main_tv;
    TextView book_read_page_no;

    SeekBar book_read_font_size_sb;


    public CustomiseTextBottomSheet(BookReadActivity bookReadActivity, TextView book_read_main_tv, TextView book_read_page_no) {
        super(bookReadActivity);
        this.bookReadActivity = bookReadActivity;
        this.book_read_main_tv=book_read_main_tv;
        this.book_read_page_no=book_read_page_no;
        setContentView(R.layout.fragment_customise_text_bottom_sheet);


        book_read_font_style_rv = findViewById(R.id.book_read_font_style_rv);
        book_read_font_color_rv = findViewById(R.id.book_read_font_color_rv);
        book_read_font_size_sb = findViewById(R.id.book_read_font_size_sb);

        fontStyles = new ArrayList<>();
        fontStyles.add(new FontStyleModel("Font 1"));
        fontStyles.add(new FontStyleModel("Font 2"));
        fontStyles.add(new FontStyleModel("Font 3"));
        fontStyles.add(new FontStyleModel("Font 4"));

        fontStyleAdapter = new FontStyleAdapter(getContext(), fontStyles);
        LinearLayoutManager styleManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        book_read_font_style_rv.setLayoutManager(styleManager);
        book_read_font_style_rv.setAdapter(fontStyleAdapter);

        pageColors = new ArrayList<>();

        pageColors.add(new PageColorModel(ContextCompat.getColor(getContext(),R.color.background_color),ContextCompat.getColor(getContext(),R.color.background_contrast)));
        pageColors.add(new PageColorModel(Color.parseColor("#fcf8f3"),Color.parseColor("#053220")));
        pageColors.add(new PageColorModel(Color.parseColor("#2d4059"),Color.parseColor("#fcf8f3")));
        pageColors.add(new PageColorModel(Color.parseColor("#aedadd"),Color.parseColor("#053220")));
        pageColors.add(new PageColorModel(Color.parseColor("#053220"),Color.parseColor("#f6f6f6")));
        pageColors.add(new PageColorModel(Color.parseColor("#000000"),Color.parseColor("#ffffff")));
        pageColors.add(new PageColorModel(Color.parseColor("#adccc7"),Color.parseColor("#053220")));

        pageColorAdapter = new PageColorAdapter(getContext(), pageColors, book_read_main_tv, book_read_page_no, bookReadActivity);
        LinearLayoutManager colorManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        book_read_font_color_rv.setLayoutManager(colorManager);
        book_read_font_color_rv.setAdapter(pageColorAdapter);

        book_read_font_size_sb.setProgress(bookReadActivity.seekbarProgress);

        book_read_font_size_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                book_read_main_tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12+4*progress);
                bookReadActivity.seekbarProgress=progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



    }


}