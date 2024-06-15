package com.booki.ai.activity;


import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.booki.ai.R;
import com.bumptech.glide.Glide;
import com.factor.bouncy.BouncyNestedScrollView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ncorti.slidetoact.SlideToActView;

import java.io.File;
import java.util.Date;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class Book_MarketDisplayActivity extends Activity {

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

    String book_key;
    CardView back_card, ai_card;

    ChipGroup book_library_category_group;
    boolean toggleReadMore=false;
    BouncyNestedScrollView book_library_book_display_scrollview;
    SlideToActView book_library_read_slider;
    BlurView background_img_blurview;
    Query getBookByKey;
    ConstraintLayout main_constraint_layout;
    StorageReference epubRef;
    DatabaseReference dbRef;
    private final BroadcastReceiver downloadReceiver = new DownloadReceiver();
    String userId;
    File book_epub_dir;
    File book_epub_file;
    boolean bookPurchased=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_marketdisplay);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);

        book_key = getIntent().getStringExtra("book_id");
        userId = FirebaseAuth.getInstance().getUid();

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
        back_card = findViewById(R.id.book_library_back_card);
        ai_card = findViewById(R.id.booklibrary_ai_card);

        dbRef = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased").child(book_key);

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookPurchased = snapshot.exists();
                if(snapshot.exists())
                {
                    book_library_read_slider.setText("Read now");
                }
                else
                {
                    book_library_read_slider.setText("Buy now");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        back_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        ai_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Book_MarketDisplayActivity.this, AI_ChatbotActivity.class);
                intent.putExtra("book_id", book_key);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

        book_library_read_slider.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                book_epub_dir = new File(getApplicationContext().getExternalFilesDir(null),book_key);

                if(!bookPurchased)
                {
                    dbRef.setValue("");
                }
                dbRef.child("last_read").setValue(ServerValue.TIMESTAMP);



                book_epub_file = new File(book_epub_dir,"book_epub.epub");
                if(book_epub_file.exists() && bookPurchased)
                {
                    Toast.makeText(Book_MarketDisplayActivity.this, "book exists", Toast.LENGTH_SHORT).show();
                    Intent ii = new Intent(Book_MarketDisplayActivity.this,BookReadActivity.class);
                    ii.putExtra("epubFilePath",book_epub_file.getPath());
                    startActivity(ii);
                }
                else if(!book_epub_file.exists()){
                    Toast.makeText(Book_MarketDisplayActivity.this, "doesn't exist", Toast.LENGTH_SHORT).show();
                    downloadEpub(book_epub_dir);
                }
                else if(!bookPurchased)
                {
                    Intent ii = new Intent(Book_MarketDisplayActivity.this,MainActivity.class);
                    ii.putExtra("fragment_number",R.id.librarymenu);
                    startActivity(ii);
                }
            }
        });

        fetchdata();

    }

    private void downloadEpub(File book_epub_dir){
        epubRef = FirebaseStorage.getInstance().getReference("Marketplace").child("Books").child(book_key).child("book_epub");

//        TO DOWNLOAD EPUB
        epubRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                String fileName = "book_epub.epub";

                    DownloadManager downloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);

                    DownloadManager.Request request = new DownloadManager.Request(uri);

                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    if (!book_epub_dir.exists()) {
                        book_epub_dir.mkdirs();
                    }

                    File file = new File(book_epub_dir, fileName);

                    Toast.makeText(getApplicationContext(), "downloading book", Toast.LENGTH_SHORT).show();
                    request.setDestinationUri(Uri.fromFile(file));

                    // Enqueue the download request
                    long downloadId = downloadManager.enqueue(request);

                    // Save the download ID in SharedPreferences or a similar persistent storage
                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("downloads", BookReadActivity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putLong("download_id", downloadId);
                    editor.apply();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void fetchdata() {

        //Connecting database
        DatabaseReference dbReference = FirebaseDatabase.getInstance().getReference("Marketplace").child("Books").child(book_key);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("Marketplace").child("Books").child(book_key).child("book_cover");

        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                book_library_book_name.setText(snapshot.child("book_name").getValue(String.class));
                book_library_book_author.setText(snapshot.child("book_author").getValue(String.class));
                book_library_streak_tv.setText(snapshot.child("streak_requirement").getValue(Integer.class).toString());
                book_library_price_tv.setText(snapshot.child("book_price").getValue(Float.class).toString());
                book_library_book_summary_content.setText(snapshot.child("Blurb").getValue(String.class));
                book_library_additional_stats_pages_tv.setText(snapshot.child("pages").getValue(Integer.class).toString());
                book_library_additional_stats_downloads_tv.setText(snapshot.child("downloads").getValue(Integer.class).toString());

//                Adding chips
                DataSnapshot tagList = snapshot.child("Tags");
                for(DataSnapshot snap : tagList.getChildren())
                {
                    Chip chip = new Chip(Book_MarketDisplayActivity.this);
                    chip.setText(snap.getKey());
                    chip.setTextSize(1,12);
                    chip.setChipCornerRadius(360);
                    book_library_category_group.addView(chip);
                }

                Glide.with(getApplicationContext())
                        .load(storageReference)
                        .fitCenter()
                        .into(book_library_background_img);

                Glide.with(getApplicationContext())
                        .load(storageReference)
                        .fitCenter()
                        .into(book_library_book_cover_img);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class DownloadReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadId != -1) {
                // Retrieve the stored download ID
                SharedPreferences preferences = context.getSharedPreferences("downloads", Context.MODE_PRIVATE);
                long storedDownloadId = preferences.getLong("download_id", -1);

                if (downloadId == storedDownloadId) {
                    DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Query query = new DownloadManager.Query();
                    query.setFilterById(downloadId);
                    Cursor cursor = downloadManager.query(query);
                    if (cursor.moveToFirst()) {
                        int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int status = cursor.getInt(statusIndex);
                        if (status == DownloadManager.STATUS_SUCCESSFUL) {
                            // Download completed successfully
                            Toast.makeText(context, "Book downloaded successfully", Toast.LENGTH_SHORT).show();

                            if(bookPurchased)
                            {

                                Intent ii = new Intent(getApplicationContext(),BookReadActivity.class);
                                ii.putExtra("epubFilePath",book_epub_file.getPath());
                                startActivity(ii);
                            }
                            else
                            {
                                Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                                ii.putExtra("fragment_number",R.id.librarymenu);
                                startActivity(ii);
                            }
                        } else {
                            // Download failed, handle the error
                        }
                    }
                    cursor.close();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the BroadcastReceiver
        unregisterReceiver(downloadReceiver);
    }
}

