package com.booki.ai.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;
import com.booki.ai.R;
import com.booki.ai.activity.OnboardingActivity;
import com.google.android.material.button.MaterialButton;

public class Onboarding_PagegoalsFragment extends Fragment {
    MaterialButton pagegoals1_btn, pagegoals2_btn, pagegoals3_btn, pagegoals4_btn;
    ImageView cat_image;
    TextView cat_text;
    static int page_goal = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding__pagegoals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagegoals1_btn = view.findViewById(R.id.fragment_pagegoals_1);
        pagegoals2_btn = view.findViewById(R.id.fragment_pagegoals_2);
        pagegoals3_btn = view.findViewById(R.id.fragment_pagegoals_3);
        pagegoals4_btn = view.findViewById(R.id.fragment_pagegoals_4);
        cat_image = view.findViewById(R.id.fragment_pagegoals_cat);
        cat_text = view.findViewById(R.id.fragment_pagegoals_cattextview);

        pagegoals1_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_image.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_image.animate().alpha(1).setDuration(200).start();
                        cat_image.setImageResource(R.drawable.cat1);
                        }
                }).start();

                cat_text.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_text.animate().alpha(1).setDuration(200).start();
                        cat_text.setText("\"meow... the leisurely reader, taking it one purrfect page at a time.\nI envy your patience!\"");
                    }
                }).start();

                pagegoals1_btn.setIconSize(15);
                pagegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                pagegoals1_btn.setTextColor(getResources().getColor(R.color.background_color));
                pagegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                pagegoals1_btn.setStrokeWidth(10);

                pagegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals2_btn.setStrokeWidth(1);

                pagegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals3_btn.setStrokeWidth(1);

                pagegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals4_btn.setStrokeWidth(1);

                page_goal = 1;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        pagegoals2_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_image.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_image.animate().alpha(1).setDuration(200).start();
                        cat_image.setImageResource(R.drawable.cat2);
                    }
                }).start();
                cat_text.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_text.animate().alpha(1).setDuration(200).start();
                        cat_text.setText("\"That's nice and cozy, just like a cat curled up by the fireplace\nKeep those pages turning\"");
                    }
                }).start();

                pagegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals1_btn.setStrokeWidth(1);
                pagegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                pagegoals2_btn.setTextColor(getResources().getColor(R.color.background_color));
                pagegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                pagegoals2_btn.setStrokeWidth(10);
                pagegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals3_btn.setStrokeWidth(1);
                pagegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals4_btn.setStrokeWidth(1);

                page_goal = 2;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        pagegoals3_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_image.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_image.animate().alpha(1).setDuration(200).start();
                        cat_image.setImageResource(R.drawable.cat3);
                    }
                }).start();
                cat_text.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_text.animate().alpha(1).setDuration(200).start();
                        cat_text.setText("\"Ah, a purr-fectly balanced routine, like curling up in a warm sunbeam\nMeowvelous!\"");
                    }
                }).start();

                pagegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals1_btn.setStrokeWidth(1);
                pagegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals2_btn.setStrokeWidth(1);
                pagegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                pagegoals3_btn.setTextColor(getResources().getColor(R.color.background_color));
                pagegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                pagegoals3_btn.setStrokeWidth(10);
                pagegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals4_btn.setStrokeWidth(1);

                page_goal = 3;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        pagegoals4_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cat_image.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_image.animate().alpha(1).setDuration(200).start();
                        cat_image.setImageResource(R.drawable.cat4);
                    }
                }).start();
                cat_text.animate().alpha(0).setDuration(200).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        cat_text.animate().alpha(1).setDuration(200).start();
                        cat_text.setText("\"Hold your bookmarks! We have got a serious reader here\"");
                    }
                }).start();

                pagegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals1_btn.setStrokeWidth(1);
                pagegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals2_btn.setStrokeWidth(1);
                pagegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                pagegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                pagegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                pagegoals3_btn.setStrokeWidth(1);
                pagegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                pagegoals4_btn.setTextColor(getResources().getColor(R.color.background_color));
                pagegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                pagegoals4_btn.setStrokeWidth(10);

                page_goal = 4;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
    }

    public static int getPage_goal() {
        return page_goal;
    }
}