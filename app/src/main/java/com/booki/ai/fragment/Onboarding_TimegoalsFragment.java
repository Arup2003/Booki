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

import com.booki.ai.R;
import com.booki.ai.activity.OnboardingActivity;
import com.google.android.material.button.MaterialButton;

public class Onboarding_TimegoalsFragment extends Fragment {
    MaterialButton timegoals1_btn, timegoals2_btn, timegoals3_btn, timegoals4_btn;
    ImageView cat_image;
    TextView cat_text;
    static int time_goals = 1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding__timegoals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timegoals1_btn = view.findViewById(R.id.fragment_timegoals_1);
        timegoals2_btn = view.findViewById(R.id.fragment_timegoals_2);
        timegoals3_btn = view.findViewById(R.id.fragment_timegoals_3);
        timegoals4_btn = view.findViewById(R.id.fragment_timegoals_4);

        cat_image = view.findViewById(R.id.fragment_timegoals_cat);
        cat_text = view.findViewById(R.id.fragment_timegoals_cattextview);

        timegoals1_btn.setOnClickListener(new View.OnClickListener() {
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

                timegoals1_btn.setIconSize(15);
                timegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                timegoals1_btn.setTextColor(getResources().getColor(R.color.background_color));
                timegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                timegoals1_btn.setStrokeWidth(10);

                timegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals2_btn.setStrokeWidth(1);

                timegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals3_btn.setStrokeWidth(1);

                timegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals4_btn.setStrokeWidth(1);

                time_goals = 1;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        timegoals2_btn.setOnClickListener(new View.OnClickListener() {
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

                timegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals1_btn.setStrokeWidth(1);
                timegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                timegoals2_btn.setTextColor(getResources().getColor(R.color.background_color));
                timegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                timegoals2_btn.setStrokeWidth(10);
                timegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals3_btn.setStrokeWidth(1);
                timegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals4_btn.setStrokeWidth(1);

                time_goals = 2;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        timegoals3_btn.setOnClickListener(new View.OnClickListener() {
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

                timegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals1_btn.setStrokeWidth(1);
                timegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals2_btn.setStrokeWidth(1);
                timegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                timegoals3_btn.setTextColor(getResources().getColor(R.color.background_color));
                timegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                timegoals3_btn.setStrokeWidth(10);
                timegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals4_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals4_btn.setStrokeWidth(1);

                time_goals = 3;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        timegoals4_btn.setOnClickListener(new View.OnClickListener() {
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

                timegoals1_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals1_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals1_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals1_btn.setStrokeWidth(1);
                timegoals2_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals2_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals2_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals2_btn.setStrokeWidth(1);
                timegoals3_btn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                timegoals3_btn.setTextColor(getResources().getColor(R.color.background_contrast));
                timegoals3_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                timegoals3_btn.setStrokeWidth(1);
                timegoals4_btn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                timegoals4_btn.setTextColor(getResources().getColor(R.color.background_color));
                timegoals4_btn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                timegoals4_btn.setStrokeWidth(10);

                time_goals = 4;
                OnboardingActivity.Enable_next_btn(true);
            }
        });
    }

    public static int getTime_goal() {
        return time_goals;
    }
}