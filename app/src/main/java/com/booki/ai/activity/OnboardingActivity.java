package com.booki.ai.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;
import com.booki.ai.R;
import com.booki.ai.fragment.InfiniteSnippetsFragment;
import com.booki.ai.fragment.Onboarding_AgeGenderFragment;
import com.booki.ai.fragment.Onboarding_InterestsFragment;
import com.booki.ai.fragment.Onboarding_PagegoalsFragment;
import com.booki.ai.fragment.Onboarding_SnippetFragment;
import com.booki.ai.fragment.Onboarding_TimegoalsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OnboardingActivity extends AppCompatActivity {
    static boolean enable_next = false;

    String gender;
    int age, page_goals, time_goals;
    ArrayList<String> interests_selected = new ArrayList<>();
    private int current_fragment = 0;
    static SlideToActView slider_next, splash_slider;
    ProgressBar progressBar;
    FadeOutParticleFrameLayout splash_heading_particle, splash_welcome_particle, splash_subheading_particle;
    CardView splash_card;
    ImageView splash_cat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        slider_next = findViewById(R.id.activity_onboarding_slider);
        progressBar = findViewById(R.id.activity_onboarding_progressbar);
        splash_heading_particle = findViewById(R.id.activity_onboarding_splash_heading);
        splash_subheading_particle = findViewById(R.id.activity_onboarding_splash_subheading);
        splash_slider = findViewById(R.id.activity_onboarding_slider_end);
        splash_welcome_particle = findViewById(R.id.activity_onboarding_splash_welcome);
        splash_cat = findViewById(R.id.activity_onboarding_splash_cat);

        splash_card = findViewById(R.id.activity_onboarding_splash_card);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.activity_oboarding_frame, new Onboarding_AgeGenderFragment());
        ft.commit();

        splash_slider.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                splash_heading_particle.startAnimation();
                splash_subheading_particle.startAnimation();

                DatabaseReference user_reference= FirebaseDatabase.getInstance().getReference("Userdata").child(FirebaseAuth.getInstance().getUid());
                Map<String, Object> user_data_map = new HashMap<>();
                user_data_map.put("age", age);
                user_data_map.put("gender", gender);
                user_data_map.put("page_goals", page_goals);
                user_data_map.put("time_goals", page_goals);
                user_reference.updateChildren(user_data_map);
                for (String i : interests_selected){
                    user_reference.child("Interested_tags").child(i).setValue("");
                }

                Intent intent = new Intent(OnboardingActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        slider_next.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NonNull SlideToActView slideToActView) {
                slider_next.setCompleted(false, true);
                if (enable_next) {
                    current_fragment += 1;
                    enable_next = false;

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                    if (current_fragment == 1) {
                        ObjectAnimator.ofInt(progressBar, "progress", 40)
                                .setDuration(800)
                                .start();
                        gender = Onboarding_AgeGenderFragment.getGender();
                        age = Onboarding_AgeGenderFragment.getAge();
                        ft.replace(R.id.activity_oboarding_frame, new Onboarding_InterestsFragment());
                    } else if (current_fragment == 2) {
                        ObjectAnimator.ofInt(progressBar, "progress", 60)
                                .setDuration(800)
                                .start();
                        interests_selected = Onboarding_InterestsFragment.getChips_selected();
                        ft.replace(R.id.activity_oboarding_frame, new Onboarding_PagegoalsFragment());
                    } else if (current_fragment == 3) {
                        ObjectAnimator.ofInt(progressBar, "progress", 80)
                                .setDuration(800)
                                .start();
                        page_goals = Onboarding_PagegoalsFragment.getPage_goal();
                        ft.replace(R.id.activity_oboarding_frame, new Onboarding_TimegoalsFragment());
                    } else if (current_fragment == 4) {
                        ObjectAnimator.ofInt(progressBar, "progress", 100)
                                .setDuration(800)
                                .start();
                        time_goals = Onboarding_TimegoalsFragment.getTime_goal();
                        ft.replace(R.id.activity_oboarding_frame, new Onboarding_SnippetFragment());
                    }
                    else if(current_fragment == 5) {
                        interests_selected.addAll(Onboarding_SnippetFragment.getSnippet_tags());

                        splash_card.setVisibility(View.VISIBLE);
                        Animation animation_splash = AnimationUtils.loadAnimation(OnboardingActivity.this, R.anim.splash_animation);
                        Animation animation_fade = AnimationUtils.loadAnimation(OnboardingActivity.this, R.anim.fade);
                        animation_splash.setDuration(2000);
                        animation_splash.setFillAfter(true);
                        splash_card.setVisibility(View.VISIBLE);
                        splash_card.startAnimation(animation_splash);
                        animation_splash.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }
                            @Override
                            public void onAnimationEnd(Animation animation) {
                                splash_welcome_particle.setVisibility(View.VISIBLE);
                                splash_welcome_particle.setAlpha(0);

                                splash_welcome_particle.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        splash_welcome_particle.setAnimationDuration(3500);
                                        splash_welcome_particle.startAnimation();

                                        splash_heading_particle.setVisibility(View.VISIBLE);
                                        splash_heading_particle.setAlpha(0);
                                        splash_heading_particle.animate().alpha(1).setDuration(1000).start();
                                        splash_welcome_particle.addAnimatorListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                splash_subheading_particle.setVisibility(View.VISIBLE);
                                                splash_subheading_particle.setAlpha(0);

                                                splash_subheading_particle.animate().alpha(1).setDuration(1000).withEndAction(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        splash_cat.setVisibility(View.VISIBLE);
                                                        splash_welcome_particle.setAlpha(0);
                                                        splash_cat.setAlpha(0f);
                                                        splash_cat.animate().alpha(1).setDuration(1000).start();

                                                        splash_slider.setVisibility(View.VISIBLE);
                                                        splash_slider.setAlpha(0);
                                                        splash_slider.animate().alpha(1).setDuration(1000).start();
                                                    }
                                                }).start();
                                            }
                                        });

                                    }
                                }).start();
                                splash_cat.animate().alpha(1).setDuration(1000).start();


                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });



                    }
                    ft.commit();
                }
                else{
                    Toast.makeText(OnboardingActivity.this, "Please select something", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public static void Enable_next_btn(boolean enable_next_btn){
        enable_next = enable_next_btn;
    }
}