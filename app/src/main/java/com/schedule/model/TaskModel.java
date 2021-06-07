package com.schedule.model;

import androidx.databinding.ObservableBoolean;

import com.schedule.Task;

import static com.schedule.Task.DayOfWeek.FRIDAY;
import static com.schedule.Task.DayOfWeek.MONDAY;
import static com.schedule.Task.DayOfWeek.SATURDAY;
import static com.schedule.Task.DayOfWeek.SUNDAY;
import static com.schedule.Task.DayOfWeek.THURSDAY;
import static com.schedule.Task.DayOfWeek.TUESDAY;
import static com.schedule.Task.DayOfWeek.WEDNESDAY;

public abstract class TaskModel {

    protected final Task task;
    public Task getTask(){
        return task;
    }

    public ObservableBoolean stateMonday = new ObservableBoolean(false);
    public ObservableBoolean stateTuesday = new ObservableBoolean(false);
    public ObservableBoolean stateWednesday = new ObservableBoolean(false);
    public ObservableBoolean stateThursday = new ObservableBoolean(false);
    public ObservableBoolean stateFriday = new ObservableBoolean(false);
    public ObservableBoolean stateSaturday = new ObservableBoolean(false);
    public ObservableBoolean stateSunday = new ObservableBoolean(false);

    public TaskModel(Task task) {
        this.task = task;
    }

    public abstract void onNumChange(int hour, int minute);

    public abstract void onSelectDaysGroupPressed();

    public void onRepeatChanged(boolean isChecked){
        task.repeat = isChecked;
    }

    public void updateSelectedDays() {
        for (Task.DayOfWeek day: task.daysOfWeek){
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

    public void getSelectedDays() {
        task.daysOfWeek.clear();

        if (stateMonday.get())
            task.daysOfWeek.add(MONDAY);

        if (stateTuesday.get())
            task.daysOfWeek.add(TUESDAY);

        if (stateWednesday.get())
            task.daysOfWeek.add(WEDNESDAY);

        if (stateThursday.get())
            task.daysOfWeek.add(THURSDAY);

        if (stateFriday.get())
            task.daysOfWeek.add(FRIDAY);

        if (stateSaturday.get())
            task.daysOfWeek.add(SATURDAY);

        if (stateSunday.get())
            task.daysOfWeek.add(SUNDAY);
    }
}
