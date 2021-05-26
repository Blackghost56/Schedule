package com.schedule.tools;

import androidx.databinding.BindingAdapter;

import com.google.android.material.chip.ChipGroup;

public class BindingTools {



    @BindingAdapter("onTimeChanged")
    public static void setOnTimeChanged(TimePicker timePicker, TimePicker.OnTimeChangedListener listener) {
        timePicker.setOnTimeChangedListener(listener);
    }



    @BindingAdapter("onCheckedChange")
    public static void setOnCheckedChange(ChipGroup chipGroup, ChipGroup.OnCheckedChangeListener listener) {
        chipGroup.setOnCheckedChangeListener(listener);
    }
}
