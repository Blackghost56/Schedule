package com.schedule.model;

import android.util.Log;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.schedule.tools.TimePicker;

public class NewTaskViewModel extends ViewModel {

    final private String TAG = NewTaskViewModel.class.getSimpleName();


    public void onNumChange(TimePicker view, int hour, int minute){
//        new TimePicker().setOnTimeChangedListener((view1, hour1, minute1) -> {
//
//        });
        Log.d(TAG, "onNumChange, hour: " + hour + "\t minute: " + minute);
    }
}