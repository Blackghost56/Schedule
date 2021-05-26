package com.schedule.model;

import android.util.Log;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

import com.google.android.material.chip.ChipGroup;
import com.schedule.R;
import com.schedule.tools.TimePicker;

public class NewTaskViewModel extends ViewModel {

    final private String TAG = NewTaskViewModel.class.getSimpleName();


    public void onNumChange(TimePicker view, int hour, int minute){
        Log.d(TAG, "onNumChange, hour: " + hour + "\t minute: " + minute);
    }

    public void onCheckedChange(ChipGroup group, int checkedId){
        Log.d(TAG, "onCheckedChange, id: " + checkedId);

        switch (checkedId){
            case R.id.chipOnce:
                break;
            case R.id.chipDaily:
                break;
            case R.id.chipWeekdays:
                break;
            case R.id.chipSelectDays:
                break;
        }
    }


    public void onOncePressed(){

    }
}