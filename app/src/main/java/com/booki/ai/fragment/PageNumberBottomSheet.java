package com.booki.ai.fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.booki.ai.activity.BookReadActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.booki.ai.R;
import com.booki.ai.databinding.FragmentPageNumberBottomSheetListDialogBinding;


public class PageNumberBottomSheet extends BottomSheetDialog {


    public PageNumberBottomSheet(@NonNull BookReadActivity bookReadActivity,int totalPages,int currPage) {
        super(bookReadActivity);
        setContentView(R.layout.fragment_page_number_bottom_sheet_list_dialog);
        SeekBar book_read_page_no_sb = findViewById(R.id.book_read_page_no_sb);
        TextView book_read_page_no_bottomsheet_tv = findViewById(R.id.book_read_page_no_bottomsheet_tv);

        book_read_page_no_bottomsheet_tv.setText("Page "+currPage+" of "+totalPages);
        book_read_page_no_sb.setMax(totalPages);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            book_read_page_no_sb.setMin(1);
        }
        book_read_page_no_sb.setProgress(currPage);

        book_read_page_no_sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                book_read_page_no_bottomsheet_tv.setText("Page "+progress+" of "+totalPages);
                BookReadActivity.scrollToPage(progress);
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