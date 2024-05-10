package com.booki.ai.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.booki.ai.R;
import com.booki.ai.activity.OnboardingActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Onboarding_AgeGenderFragment extends Fragment {
    static String gender;
    static int age;

    NumberPicker numberPicker;
    MaterialButton malebtn, femalebtn, othersbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding__age_gender, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        numberPicker = view.findViewById(R.id.fragment_age_gender_age_picker);
        malebtn = view.findViewById(R.id.fragment_age_gender_male_btn);
        femalebtn = view.findViewById(R.id.fragment_age_gender_female_btn);
        othersbtn = view.findViewById(R.id.fragment_age_gender_others_btn);

        numberPicker.setMinValue(13);
        numberPicker.setMaxValue(69);
        numberPicker.setValue(13);


        malebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "male";

                malebtn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                malebtn.setTextColor(getResources().getColor(R.color.background_color));
                malebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                malebtn.setStrokeWidth(10);
                femalebtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                femalebtn.setTextColor(getResources().getColor(R.color.background_contrast));
                femalebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                femalebtn.setStrokeWidth(1);
                othersbtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                othersbtn.setTextColor(getResources().getColor(R.color.background_contrast));
                othersbtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                othersbtn.setStrokeWidth(1);

                OnboardingActivity.Enable_next_btn(true);
            }
        });
        femalebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "female";
                femalebtn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                femalebtn.setTextColor(getResources().getColor(R.color.background_color));
                femalebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                femalebtn.setStrokeWidth(10);
                malebtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                malebtn.setTextColor(getResources().getColor(R.color.background_contrast));
                malebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                malebtn.setStrokeWidth(1);
                othersbtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                othersbtn.setTextColor(getResources().getColor(R.color.background_contrast));
                othersbtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                othersbtn.setStrokeWidth(1);
                OnboardingActivity.Enable_next_btn(true);
            }
        });
        othersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = "others";
                othersbtn.setBackgroundColor(getResources().getColor(R.color.background_contrast));
                othersbtn.setTextColor(getResources().getColor(R.color.background_color));
                othersbtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.booki_orange)));
                othersbtn.setStrokeWidth(10);
                femalebtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                femalebtn.setTextColor(getResources().getColor(R.color.background_contrast));
                femalebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                femalebtn.setStrokeWidth(1);
                malebtn.setBackgroundColor(getResources().getColor(R.color.background_surface));
                malebtn.setTextColor(getResources().getColor(R.color.background_contrast));
                malebtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                malebtn.setStrokeWidth(1);

                OnboardingActivity.Enable_next_btn(true);
            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                age = newVal;
            }
        });
    }

    public static String getGender() {
        return gender;
    }

    public static  int getAge() {
        return age;
    }
}