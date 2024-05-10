package com.booki.ai.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.appgozar.fadeoutparticle.FadeOutParticleFrameLayout;
import com.booki.ai.R;
import com.booki.ai.activity.OnboardingActivity;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class Onboarding_InterestsFragment extends Fragment {
    ChipGroup chipGroup;
    int chips_selected_num = 0;
    static ArrayList<String> chips_selected = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onboarding__interests, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipGroup = view.findViewById(R.id.fragment_interests_chipgroup);

        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int index = chipGroup.indexOfChild(buttonView);
                    chipGroup.removeView(buttonView);
                    chipGroup.addView(buttonView, index);
                    if (chip.isChecked()) {
                        chips_selected_num += 1;
                        chips_selected.add(chip.getText().toString());
                        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_contrast)));
                        chip.setTextColor(getResources().getColor(R.color.background_color));
                        chip.setCheckedIconTint(ColorStateList.valueOf(getResources().getColor(R.color.background_color)));
                    }
                    else{
                        chips_selected_num -= 1;
                        chips_selected.remove(chip.getText().toString());
                        chip.setChipBackgroundColor(ColorStateList.valueOf(getResources().getColor(R.color.background_surface)));
                        chip.setTextColor(getResources().getColor(R.color.background_contrast));
                        chip.setCheckedIconTint(ColorStateList.valueOf(getResources().getColor(R.color.background_color)));
                    }
                    for(String i:chips_selected){
                        System.out.println(i);
                    }
                    OnboardingActivity.Enable_next_btn(chips_selected_num != 0);
                }
            });
        }
    }

    public static ArrayList<String> getChips_selected() {
        return chips_selected;
    }
}