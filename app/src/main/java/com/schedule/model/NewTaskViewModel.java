package com.schedule.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.schedule.R;

import java.util.Calendar;
import java.util.GregorianCalendar;


public class NewTaskViewModel extends AndroidViewModel {

    final private String TAG = NewTaskViewModel.class.getSimpleName();

    public ObservableField<String> leftTimeText = new ObservableField<>("");

    public ObservableBoolean stateOnce = new ObservableBoolean(true);
    public ObservableBoolean stateDaily = new ObservableBoolean(false);
    public ObservableBoolean stateWeekdays = new ObservableBoolean(false);
    public ObservableBoolean stateSelectDays = new ObservableBoolean(false);

    public ObservableBoolean enableDays = new ObservableBoolean(false);

    public ObservableBoolean stateMonday = new ObservableBoolean(false);
    public ObservableBoolean stateTuesday = new ObservableBoolean(false);
    public ObservableBoolean stateWednesday = new ObservableBoolean(false);
    public ObservableBoolean stateThursday = new ObservableBoolean(false);
    public ObservableBoolean stateFriday = new ObservableBoolean(false);
    public ObservableBoolean stateSaturday = new ObservableBoolean(false);
    public ObservableBoolean stateSunday = new ObservableBoolean(false);


    private int hour;
    private int minute;

    public NewTaskViewModel(@NonNull Application application) {
        super(application);
    }

    public void onNumChange(int hour, int minute){
        Log.d(TAG, "onNumChange, hour: " + hour + "\t minute: " + minute);
        this.hour = hour;
        this.minute = minute;
        updateLeftTime();
    }


    public void onOncePressed(){
        daysViewUpdate();
        updateLeftTime();
    }

    public void onDailyPressed(){
        daysViewUpdate();
        updateLeftTime();
    }

    public void onWeekdaysPressed(){
        daysViewUpdate();
        updateLeftTime();
    }

    public void onSelectDaysPressed(){
        daysViewUpdate();
        updateLeftTime();
    }

    private void daysViewUpdate(){
        enableDays.set(stateSelectDays.get());
        updateLeftTime();
    }


    private void updateLeftTime(){
        final int MINUTE_IN_DAY = 1440;
        Calendar calendar = GregorianCalendar.getInstance();
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute =  calendar.get(Calendar.MINUTE);

        int currentTimeInMinute = currentHour * 60 + currentMinute;
        int timeInMinute = hour * 60 + minute;

        int delta = 0;
        if (stateOnce.get() || stateDaily.get()){

            if (timeInMinute < currentTimeInMinute){
                delta = MINUTE_IN_DAY - currentTimeInMinute + timeInMinute;
            } else {
                delta = timeInMinute - currentTimeInMinute;
            }
        }

        updateLeftTimeText(delta);
    }

    private void updateLeftTimeText(int minute){
        int min = minute % 60;
        int hour = minute / 60;

        leftTimeText.set(getApplication().getString(R.string.schedule_in_a) + " " +
                hour + " " + getApplication().getString(R.string.schedule_hour) + " " +
                min + " " + getApplication().getString(R.string.schedule_minute)
        );
    }



}