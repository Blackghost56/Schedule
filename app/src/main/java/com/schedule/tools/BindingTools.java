package com.schedule.tools;

import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;

import com.google.android.material.chip.ChipGroup;
import com.schedule.Task;

public class BindingTools {



    @BindingAdapter("onTimeChanged")
    public static void setOnTimeChanged(TimePicker timePicker, TimePicker.OnTimeChangedListener listener) {
        timePicker.setOnTimeChangedListener(listener);
    }

    @BindingAdapter("initValue")
    public static void setInitValue(TimePicker timePicker, Task.TimeOfDay timeOfDay) {
        timePicker.setHour(timeOfDay.getHour());
        timePicker.setMinute(timeOfDay.getMinute());
    }

    @BindingAdapter("onCheckedChange")
    public static void setOnCheckedChange(SwitchCompat switchCompat, CompoundButton.OnCheckedChangeListener listener) {
        switchCompat.setOnCheckedChangeListener(listener);
    }


}
