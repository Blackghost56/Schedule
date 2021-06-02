package com.schedule.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.schedule.Task;
import com.schedule.Task.DayOfWeek;
import com.schedule.tools.SingleLiveEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.schedule.Task.DayOfWeek.FRIDAY;
import static com.schedule.Task.DayOfWeek.MONDAY;
import static com.schedule.Task.DayOfWeek.SATURDAY;
import static com.schedule.Task.DayOfWeek.SUNDAY;
import static com.schedule.Task.DayOfWeek.THURSDAY;
import static com.schedule.Task.DayOfWeek.TUESDAY;
import static com.schedule.Task.DayOfWeek.WEDNESDAY;


public class NewTaskViewModel extends AndroidViewModel {

//    final private String TAG = NewTaskViewModel.class.getSimpleName();

    protected final SingleLiveEvent<Void> actionPopBackStack = new SingleLiveEvent<>();
    public LiveData<Void> getActionPopBackStack(){
        return actionPopBackStack;
    }

    public ObservableField<String> leftTimeText = new ObservableField<>("");

    public ObservableBoolean stateMonday = new ObservableBoolean(false);
    public ObservableBoolean stateTuesday = new ObservableBoolean(false);
    public ObservableBoolean stateWednesday = new ObservableBoolean(false);
    public ObservableBoolean stateThursday = new ObservableBoolean(false);
    public ObservableBoolean stateFriday = new ObservableBoolean(false);
    public ObservableBoolean stateSaturday = new ObservableBoolean(false);
    public ObservableBoolean stateSunday = new ObservableBoolean(false);

    private final Task task = new Task();

    public NewTaskViewModel(@NonNull Application application) {
        super(application);

        try {
            Calendar calendar = GregorianCalendar.getInstance();
            task.timeOfDay.setValueHM(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            task.daysOfWeek.add(Task.dayOfWeekConvert(calendar.get(Calendar.DAY_OF_WEEK)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateSelectedDays();
        updateLeftTime();
    }

    public void onNumChange(int hour, int minute){
        try {
            task.timeOfDay.setValueHM(hour, minute);
            updateLeftTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSelectDaysGroupPressed(){
        getSelectedDays();
        updateLeftTime();
    }

    public void onRepeatChanged(boolean isChecked) {
        task.repeat = isChecked;
    }

    public void onCreatePressed(){
        task.isEnabled = true;
        actionPopBackStack.call();
    }


    private void updateLeftTime(){
        leftTimeText.set(task.leftTimeStr(getApplication().getApplicationContext()));
    }


    private void updateSelectedDays() {
        for (DayOfWeek day: task.daysOfWeek){
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