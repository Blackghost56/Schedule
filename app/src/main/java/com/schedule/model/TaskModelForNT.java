package com.schedule.model;

import android.content.Context;

import androidx.databinding.ObservableField;

import com.schedule.Task;


public class TaskModelForNT extends TaskModel {


    public ObservableField<String> leftTimeText = new ObservableField<>("");

    private final Context context;

    public TaskModelForNT(Context context, Task task) {
        super(task);

        this.context = context;

        updateSelectedDays();
        updateLeftTime();
    }


    @Override
    public void onNumChange(int hour, int minute) {
        try {
            task.timeOfDay.setValueHM(hour, minute);
            updateLeftTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSelectDaysGroupPressed() {
        getSelectedDays();
        updateLeftTime();
    }


    private void updateLeftTime(){
        leftTimeText.set(task.leftTimeStr(context));
    }

}
