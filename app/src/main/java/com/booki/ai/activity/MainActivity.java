package com.booki.ai.activity;

import static androidx.core.content.ContextCompat.startActivity;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.booki.ai.R;
import com.booki.ai.fragment.BookLibraryFragment;
import com.booki.ai.fragment.InfiniteSnippetsFragment;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
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

import java.io.File;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {
    int current_fragment;
    int initial_item_id;
    BottomNavigationView bottomNavigationView;
    ImageView menu_icon;
    TextView verify_snippet;
    BlurView action_bar_blurview, bottom_navigation_blurview;
    ImageView book_library_current_read_iv;
    CardView book_library_current_read_cv;
    static StorageReference storageReference;
    DatabaseReference databaseReference;
    String userId;
    static String book_id="";
//    BottomNavigationItemView centremenu;
    final long[] bookCount = {0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //!!ADD CHECK IF COOKIES ARE STORED OR NOT BEFORE LETTING USER CONTINUE
        if(FirebaseAuth.getInstance().getUid() == null){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            MainActivity.this.finish();
        }
        else {

            //Initialising all the views
            bottomNavigationView = findViewById(R.id.main_activity_bottomnavigation);
            menu_icon = findViewById(R.id.main_activity_menu);
            action_bar_blurview = findViewById(R.id.main_activity_actionbar_blurview);
            bottom_navigation_blurview = findViewById(R.id.main_activity_bottomnavigation_blurview);
            verify_snippet = findViewById(R.id.main_activity_verifysnippet);
            book_library_current_read_iv = findViewById(R.id.book_library_current_read_iv);
            book_library_current_read_cv = findViewById(R.id.book_library_current_read_cv);
//            centremenu = findViewById(R.id.centremenu);

            book_library_current_read_cv.setVisibility(View.GONE);

            userId = FirebaseAuth.getInstance().getUid();
            FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("last_login").setValue(ServerValue.TIMESTAMP);

            initial_item_id = getIntent().getIntExtra("fragment_number",R.id.homemenu);


            //Initialising BlurView
            View decor = getWindow().getDecorView();
            ViewGroup decorview = decor.findViewById(R.id.main_activity_constraint_layout);
            Drawable windowBackground = decor.getBackground();
            action_bar_blurview.setupWith(decorview, new RenderScriptBlur(getApplicationContext()))
                    .setFrameClearDrawable(windowBackground)
                    .setBlurRadius(10);
            bottom_navigation_blurview.setupWith(decorview, new RenderScriptBlur(getApplicationContext()))
                    .setFrameClearDrawable(windowBackground)
                    .setBlurRadius(10);

            verify_snippet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent heyintent = new Intent(MainActivity.this, SnippetVerificationAdminONLYActivity.class);
                    startActivity(heyintent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            book_library_current_read_cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Query query = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased").orderByChild("last_read").limitToLast(1);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot snap : snapshot.getChildren())
                            {
                                book_id = snap.getKey();
                            }

                            databaseReference = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased").child(book_id);
                            databaseReference.child("last_read").setValue(ServerValue.TIMESTAMP);


                            File book_epub_dir = new File(getApplicationContext().getExternalFilesDir(null),book_id);
                            File book_epub_file = new File(book_epub_dir,"book_epub.epub");

                            Intent ii = new Intent(getApplicationContext(), BookReadActivity.class);
                            ii.putExtra("epubFilePath",book_epub_file.getPath());
                            startActivity(ii);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });


            //Defining BottomNavigationView
            Menu menu = bottomNavigationView.getMenu();
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id=item.getItemId();


                    if (id == R.id.homemenu) {
                        if (current_fragment != R.id.homemenu) {
                            current_fragment = R.id.homemenu;
                            menu.findItem(R.id.homemenu).setIcon(R.drawable.home_icon_selected);
                            menu.findItem(R.id.librarymenu).setIcon(R.drawable.library_icon_notselected);
                            menu.findItem(R.id.storemenu).setIcon(R.drawable.store_icon_notselected);
                            menu.findItem(R.id.profilemenu).setIcon(R.drawable.profile_icon_notselected);

                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            ft.replace(R.id.main_activity_fragment_frame, new InfiniteSnippetsFragment());
                            ft.commit();
                        }
                    } else if (id == R.id.librarymenu) {
                        if (current_fragment != R.id.librarymenu) {
                            current_fragment = R.id.librarymenu;
                            menu.findItem(R.id.homemenu).setIcon(R.drawable.home_icon_notselected);
                            menu.findItem(R.id.librarymenu).setIcon(R.drawable.library_icon_selected);
                            menu.findItem(R.id.storemenu).setIcon(R.drawable.store_icon_notselected);
                            menu.findItem(R.id.profilemenu).setIcon(R.drawable.profile_icon_notselected);

                            FragmentManager fm = getSupportFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                            ft.replace(R.id.main_activity_fragment_frame, new BookLibraryFragment());
                            ft.commit();


                        }
                    } else if (id == R.id.storemenu) {
                        if (current_fragment != R.id.storemenu) {
                            current_fragment = R.id.storemenu;
                            menu.findItem(R.id.homemenu).setIcon(R.drawable.home_icon_notselected);
                            menu.findItem(R.id.librarymenu).setIcon(R.drawable.library_icon_notselected);
                            menu.findItem(R.id.storemenu).setIcon(R.drawable.store_icon_selected);
                            menu.findItem(R.id.profilemenu).setIcon(R.drawable.profile_icon_notselected);
                        }
                    } else if (id == R.id.profilemenu) {
                        if (current_fragment != R.id.profilemenu) {
                            current_fragment = R.id.profilemenu;
                            menu.findItem(R.id.homemenu).setIcon(R.drawable.home_icon_notselected);
                            menu.findItem(R.id.librarymenu).setIcon(R.drawable.library_icon_notselected);
                            menu.findItem(R.id.storemenu).setIcon(R.drawable.store_icon_notselected);
                            menu.findItem(R.id.profilemenu).setIcon(R.drawable.profile_icon_selected);
                        }
                    }
                    return true;
                }
            });
            bottomNavigationView.setSelectedItemId(initial_item_id);
        }
    }

    public void loadCurrentBook()
    {
        Query query = FirebaseDatabase.getInstance().getReference("Userdata").child(userId).child("books_purchased").orderByChild("last_read").limitToLast(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                bookCount[0] =snapshot.getChildrenCount();
                if(bookCount[0]!=0) {

                    book_library_current_read_cv.setVisibility(View.VISIBLE);
//                    centremenu.setVisible(true);

                    for (DataSnapshot snap : snapshot.getChildren()) {
                        book_id = snap.getKey();
                        Toast.makeText(MainActivity.this, book_id, Toast.LENGTH_SHORT).show();
                    }
                    storageReference = FirebaseStorage.getInstance().getReference("Marketplace").child("Books").child(book_id).child("book_cover");

                    Glide.with(MainActivity.this)
                            .load(storageReference)
                            .centerCrop()
                            .into(book_library_current_read_iv);
                }
                else {
                    book_library_current_read_cv.setVisibility(View.GONE);
//                    centremenu.setVisible(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCurrentBook();

    }
}