package com.schedule.model;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.ViewModel;

public class NewTaskViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    public ObservableInt minValue = new ObservableInt(0);
    public ObservableInt maxValue = new ObservableInt(59);
    public ObservableInt defaultValue = new ObservableInt(5);

    public void onNum1Change(){

    }
}