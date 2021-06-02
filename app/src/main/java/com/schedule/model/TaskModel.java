package com.schedule.model;

import android.content.Context;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;

import com.schedule.Task;

import static com.schedule.Task.DayOfWeek.FRIDAY;
import static com.schedule.Task.DayOfWeek.MONDAY;
import static com.schedule.Task.DayOfWeek.SATURDAY;
import static com.schedule.Task.DayOfWeek.SUNDAY;
import static com.schedule.Task.DayOfWeek.THURSDAY;
import static com.schedule.Task.DayOfWeek.TUESDAY;
import static com.schedule.Task.DayOfWeek.WEDNESDAY;


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
        updateSelectedDays();
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

    public void onNumChange(int hour, int minute){
        try {
            item.timeOfDay.setValueHM(hour, minute);
            time.set(item.timeToStr(context));
            updateAdditionalText();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectDaysGroupPressed(){
        getSelectedDays();
        updateAdditionalText();
    }


    public ObservableBoolean stateMonday = new ObservableBoolean(false);
    public ObservableBoolean stateTuesday = new ObservableBoolean(false);
    public ObservableBoolean stateWednesday = new ObservableBoolean(false);
    public ObservableBoolean stateThursday = new ObservableBoolean(false);
    public ObservableBoolean stateFriday = new ObservableBoolean(false);
    public ObservableBoolean stateSaturday = new ObservableBoolean(false);
    public ObservableBoolean stateSunday = new ObservableBoolean(false);


    private void updateSelectedDays() {
        for (Task.DayOfWeek day: item.daysOfWeek){
            switch (day){
                case MONDAY:
                    stateMonday.set(true);
                    break;
                case TUESDAY:
                    stateTuesday.set(true);
                    break;
                case WEDNESDAY:
                    stateWednesday.set(true);
                    break;
                case THURSDAY:
                    stateThursday.set(true);
                    break;
                case FRIDAY:
                    stateFriday.set(true);
                    break;
                case SATURDAY:
                    stateSaturday.set(true);
                    break;
                case SUNDAY:
                    stateSunday.set(true);
                    break;
            }
        }
    }

    private void getSelectedDays() {
        item.daysOfWeek.clear();

        if (stateMonday.get())
            item.daysOfWeek.add(MONDAY);

        if (stateTuesday.get())
            item.daysOfWeek.add(TUESDAY);

        if (stateWednesday.get())
            item.daysOfWeek.add(WEDNESDAY);

        if (stateThursday.get())
            item.daysOfWeek.add(THURSDAY);

        if (stateFriday.get())
            item.daysOfWeek.add(FRIDAY);

        if (stateSaturday.get())
            item.daysOfWeek.add(SATURDAY);

        if (stateSunday.get())
            item.daysOfWeek.add(SUNDAY);
    }
}
