package com.booki.ai.activity;


import static android.app.PendingIntent.getActivity;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booki.ai.R;
import com.booki.ai.model.BookDisplayModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.factor.bouncy.BouncyNestedScrollView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ncorti.slidetoact.SlideToActView;

import java.io.File;
import java.io.IOException;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class BookLibraryActivity extends Activity {

    ImageView book_library_book_cover_img;
    ImageView book_library_background_img;
    TextView book_library_book_name;
    TextView book_library_book_author;
    TextView book_library_streak_tv;
    TextView book_library_price_tv;
    TextView book_library_book_summary_content;
    TextView book_library_read_more_btn;
    TextView book_library_additional_stats_pages_tv;
    TextView book_library_additional_stats_language_tv;
    TextView book_library_additional_stats_downloads_tv;
    TextView book_library_about_author_content;

    ChipGroup book_library_category_group;
    boolean toggleReadMore=false;
    BouncyNestedScrollView book_library_book_display_scrollview;
    SlideToActView book_library_read_slider;
    BlurView background_img_blurview;


    DatabaseReference dbReference;
    StorageReference storageReference;
    Query getBookByKey;
    ConstraintLayout main_constraint_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_library);

        book_library_book_cover_img = findViewById(R.id.book_library_book_cover_img);
        book_library_background_img = findViewById(R.id.book_library_background_img);
        book_library_book_name = findViewById(R.id.book_library_book_name);
        book_library_book_author = findViewById(R.id.book_library_book_author);
        book_library_streak_tv = findViewById(R.id.book_library_streak_tv);
        book_library_price_tv = findViewById(R.id.book_library_price_tv);
        book_library_book_summary_content = findViewById(R.id.book_library_book_summary_content);
        book_library_read_more_btn = findViewById(R.id.book_library_read_more_btn);
        book_library_additional_stats_pages_tv = findViewById(R.id.book_library_additional_stats_pages_tv);
        book_library_additional_stats_language_tv = findViewById(R.id.book_library_additional_stats_language_tv);
        book_library_additional_stats_downloads_tv = findViewById(R.id.book_library_additional_stats_downloads_tv);
        book_library_about_author_content = findViewById(R.id.book_library_about_author_content);
        book_library_category_group = findViewById(R.id.book_library_category_group);
        book_library_book_display_scrollview = findViewById(R.id.book_library_book_display_scrollview);
        book_library_read_slider = findViewById((R.id.book_library_read_slider));
        background_img_blurview = findViewById(R.id.book_library_background_img_blurview);
        main_constraint_layout = findViewById(R.id.book_library_constraint_main);


//        Connecting database
        dbReference = FirebaseDatabase.getInstance().getReference("BookDisplay");
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://booki-16748.appspot.com/bookDisplay/images");



        //Initialising BlurView
          View decor = getWindow().getDecorView();
          ViewGroup decorview = decor.findViewById(R.id.book_library_constraint_main);
          Drawable windowBackground = decor.getBackground();

          background_img_blurview.setupWith(decorview, new RenderScriptBlur(getApplicationContext()))
                  .setFrameClearDrawable(windowBackground)
                  .setBlurRadius(10);


        // Example of handling chip selection
        book_library_category_group.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                Chip chip = group.findViewById(checkedId);
                // Handle chip selection here
            }
        });

//        toggling summary length
        book_library_read_more_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!toggleReadMore)
                {

                    ViewGroup.LayoutParams layoutParams = book_library_book_summary_content.getLayoutParams();
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    book_library_book_summary_content.setLayoutParams(layoutParams);

                    int total_height = layoutParams.height;


                    book_library_read_more_btn.setText("read less");
                }
                else
                {
                    ViewGroup.LayoutParams layoutParams = book_library_book_summary_content.getLayoutParams();

                    float density = Resources.getSystem().getDisplayMetrics().density;
                    layoutParams.height = (int) (80 * density + 0.5f);

                    book_library_book_summary_content.setLayoutParams(layoutParams);

                    book_library_read_more_btn.setText("read more");
                }

                toggleReadMore=!toggleReadMore;
            }
        });

        book_library_book_display_scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(oldScrollY<scrollY)
                {
                    book_library_read_slider.animate().alpha(0).start();
                }
                else
                {
                    book_library_read_slider.animate().alpha(1).start();
                }
            }
        });

//        Fetching from database;
        String bookId="1";
        getBookByKey = dbReference.child(bookId);

        getBookByKey.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String key = snapshot.getKey();

                BookDisplayModel book = snapshot.getValue(BookDisplayModel.class);
                book_library_book_name.setText(""+book.getName());
                book_library_book_author.setText(""+book.getAuthor());
                book_library_streak_tv.setText(""+book.getStreakRequirement());

                String price = ""+book.getPrice();
                book_library_price_tv.setText(""+"INR "+price);
                book_library_book_summary_content.setText(book.getSummary());

//                Adding chips
                DataSnapshot tagList = snapshot.child("tags");
                for(DataSnapshot snap : tagList.getChildren())
                {
                    Chip chip = new Chip(BookLibraryActivity.this);
                    chip.setText(""+snap.getKey());
                    chip.setTextSize(1,12);
                    chip.setChipCornerRadius(360);
                    book_library_category_group.addView(chip);
                }

                StorageReference background_image_ref = FirebaseStorage.getInstance().getReference("bookDisplay").child("images").child("background").child(key + ".jpg");
                Glide.with(getApplicationContext())
                        .load(background_image_ref)
                        .fitCenter()
                        .into(book_library_background_img);

                StorageReference cover_image_ref = FirebaseStorage.getInstance().getReference("bookDisplay").child("images").child("cover").child(key + ".jpg");
                Glide.with(getApplicationContext())
                        .load(cover_image_ref)
                        .fitCenter()
                        .into(book_library_book_cover_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

