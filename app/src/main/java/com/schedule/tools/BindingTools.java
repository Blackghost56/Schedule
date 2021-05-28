package com.schedule.tools;

import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.chip.ChipGroup;

public class BindingTools {



    @BindingAdapter("onTimeChanged")
    public static void setOnTimeChanged(TimePicker timePicker, TimePicker.OnTimeChangedListener listener) {
        timePicker.setOnTimeChangedListener(listener);
    }


    @BindingAdapter("onCheckedChange")
    public static void setOnCheckedChange(SwitchCompat switchCompat, CompoundButton.OnCheckedChangeListener listener) {
        switchCompat.setOnCheckedChangeListener(listener);
    }


}
