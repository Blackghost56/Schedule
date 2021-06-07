package com.schedule.model;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

import com.schedule.R;
import com.schedule.Task;


public class TaskModelForAdapter extends TaskModel {

    public ObservableBoolean isSelected = new ObservableBoolean(false);
    public ObservableBoolean isExpanded = new ObservableBoolean(false);
    public ObservableBoolean isModeMultiSelect = new ObservableBoolean(false);

    public void setSelected(boolean selected) {
        isSelected.set(selected);
    }
    public boolean isSelected() {
        return isSelected.get();
    }
    public void setExpanded(boolean expanded) {
        isExpanded.set(expanded);
    }
    public boolean isExpanded() {
        return isExpanded.get();
    }
    public void setModeMultiSelect(boolean mode) {
        isModeMultiSelect.set(mode);
    }
    public boolean isModeMultiSelect() {
        return isModeMultiSelect.get();
    }

    public ObservableBoolean isEnable = new ObservableBoolean(false);
    public ObservableField<String> time = new ObservableField<>("");
    public ObservableBoolean isRepeat = new ObservableBoolean(false);
    public ObservableField<String> additionalText = new ObservableField<>("");
    public ObservableInt timeColor = new ObservableInt(R.color.secondaryTextDark);

    private final Context context;

    public TaskModelForAdapter(Context context, Task item) {
        super(item);

        this.context = context;

        isEnable.set(item.isEnabled);
        time.set(item.timeToStr(context));
        isRepeat.set(item.repeat);
        updateSelectedDays();
        updateAdditionalText();
        updateTimeColor();
    }

    private void updateTimeColor(){
        if (task.isEnabled){
            timeColor.set(R.color.black);
        } else {
            timeColor.set(R.color.secondaryTextDark);
        }
    }

    private void updateAdditionalText(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(task.daysOfWeekToStr(context)).append(" | ");
        stringBuilder.append(task.repeatToStr(context));
        if (task.isEnabled)
            stringBuilder.append(" | ").append(task.leftTimeStr(context));

        additionalText.set(stringBuilder.toString());
    }

    public void onEnabledChanged(boolean isChecked){
        task.isEnabled = isChecked;
        updateAdditionalText();
        updateTimeColor();
    }

    public void onNumChange(int hour, int minute){
        try {
            task.timeOfDay.setValueHM(hour, minute);
            time.set(task.timeToStr(context));
            updateAdditionalText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectDaysGroupPressed(){
        getSelectedDays();
        updateAdditionalText();
    }

    public void onRepeatChanged(boolean isChecked) {
        task.repeat = isChecked;
        updateAdditionalText();
    }
}
