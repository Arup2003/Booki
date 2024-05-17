package com.booki.ai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.booki.ai.R;
import com.booki.ai.fragment.InfiniteSnippetsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;

public class MainActivity extends AppCompatActivity {
    int current_fragment;

    BottomNavigationView bottomNavigationView;
    ImageView menu_icon;
    TextView verify_snippet;
    BlurView action_bar_blurview, bottom_navigation_blurview;

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

            FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getUid()).child("last_login").setValue(ServerValue.TIMESTAMP);

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

            //Defining BottomNavigationView
            Menu menu = bottomNavigationView.getMenu();
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
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
            bottomNavigationView.setSelectedItemId(R.id.homemenu);
        }
    }

}