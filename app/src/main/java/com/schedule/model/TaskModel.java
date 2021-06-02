package com.schedule.model;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.schedule.Task;


public class TaskModel extends ItemModel<Task> {

    public ObservableBoolean isEnable = new ObservableBoolean(false);
    public ObservableField<String> time = new ObservableField<>("");
    public ObservableBoolean isRepeat = new ObservableBoolean(false);
    public ObservableField<String> additionalText = new ObservableField<>("");

    private Context context;

    public TaskModel(Context context, Task item) {
        super(item);

        this.context = context;

        isEnable.set(item.isEnabled);
        time.set(item.timeToStr(context));
        isRepeat.set(item.repeat);
        updateAdditionalText();
    }

    private void updateAdditionalText(){

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(item.daysOfWeekToStr(context)).append(" | ");
        stringBuilder.append(item.repeatToStr(context));
        if (item.isEnabled)
            stringBuilder.append(" | ").append(item.leftTimeStr(context));

        additionalText.set(stringBuilder.toString());
    }

    public void onEnabledChanged(boolean isChecked){

        item.isEnabled = isChecked;
        updateAdditionalText();
    }
}
