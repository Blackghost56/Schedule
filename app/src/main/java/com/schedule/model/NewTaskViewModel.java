package com.schedule.model;

import android.util.Log;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.schedule.tools.TimePicker;

public class NewTaskViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    final private String TAG = NewTaskViewModel.class.getSimpleName();

    public ObservableInt minValue = new ObservableInt(0);
    public ObservableInt maxValue = new ObservableInt(59);
    public ObservableInt defaultValue = new ObservableInt(5);

    public void onNum1Change(){

    }

    public void onNumChange(TimePicker view, int hour, int minute){
//        new TimePicker().setOnTimeChangedListener((view1, hour1, minute1) -> {
//
//        });
        Log.d(TAG, "onNumChange, hour: " + hour + "\t minute: " + minute);
    }
}