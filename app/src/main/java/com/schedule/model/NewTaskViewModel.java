package com.schedule.model;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.schedule.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;


public class NewTaskViewModel extends AndroidViewModel {

//    final private String TAG = NewTaskViewModel.class.getSimpleName();

    public ObservableField<String> leftTimeText = new ObservableField<>("");

//    public ObservableBoolean stateOnce = new ObservableBoolean(true);
//    public ObservableBoolean stateDaily = new ObservableBoolean(false);
//    public ObservableBoolean stateWeekdays = new ObservableBoolean(false);
//    public ObservableBoolean stateSelectDays = new ObservableBoolean(false);
//
//    public ObservableBoolean enableDays = new ObservableBoolean(false);

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

        Calendar calendar = GregorianCalendar.getInstance();
        final int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        List<Integer> dayList = new ArrayList<>(List.of(currentDayOfWeek));
        setSelectedDays(dayList);

        updateLeftTime();
    }

    public void onNumChange(int hour, int minute){
        this.hour = hour;
        this.minute = minute;
        updateLeftTime();
    }


//    public void onModePressed(){
//        daysViewUpdate();
//        updateLeftTime();
//    }

    public void onSelectDaysGroupPressed(){
        updateLeftTime();
    }

//    private void daysViewUpdate(){
//        enableDays.set(stateSelectDays.get());
//        updateLeftTime();
//    }

    private final int MINUTE_IN_HOUR = 60;
    private final int HOUR_IN_DAY = 24;
    private final int MINUTE_IN_DAY = MINUTE_IN_HOUR * HOUR_IN_DAY;

    // ToDo Rewrite the algorithm in a more concise way
    private void updateLeftTime(){
        Calendar calendar = GregorianCalendar.getInstance();
        final int currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        final int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        final int currentMinute =  calendar.get(Calendar.MINUTE);

//        final int currentTimeInMinute = currentHour * MINUTE_IN_HOUR + currentMinute;
//        final int timeInMinute = hour * MINUTE_IN_HOUR + minute;

        int delta = 0;
//        if (stateOnce.get() || stateDaily.get()){
//            if (timeInMinute < currentTimeInMinute){
//                delta = MINUTE_IN_DAY - currentTimeInMinute + timeInMinute;
//            } else {
//                delta = timeInMinute - currentTimeInMinute;
//            }
//        }
//
//        if (stateWeekdays.get()){
//            if (timeInMinute < currentTimeInMinute){
//                switch (currentDayOfWeek){
//                    case Calendar.FRIDAY:
//                        delta = MINUTE_IN_DAY - currentTimeInMinute + timeInMinute + MINUTE_IN_DAY * 2;
//                        break;
//                    case Calendar.SATURDAY:
//                        delta = MINUTE_IN_DAY - currentTimeInMinute + timeInMinute + MINUTE_IN_DAY;
//                        break;
//                    default:
//                        delta = MINUTE_IN_DAY - currentTimeInMinute + timeInMinute;
//                }
//            } else {
//                switch (currentDayOfWeek){
//                    case Calendar.SATURDAY:
//                        delta = timeInMinute - currentTimeInMinute + MINUTE_IN_DAY * 2;
//                        break;
//                    case Calendar.SUNDAY:
//                        delta = timeInMinute - currentTimeInMinute + MINUTE_IN_DAY;
//                        break;
//                    default:
//                        delta = timeInMinute - currentTimeInMinute;
//                }
//            }
//        }
//
//        if (stateSelectDays.get()){
//            final int currentTimeInMinuteOfWeek = dateToMin(currentDayOfWeek, currentHour, currentMinute);
//            List<Integer> selectedDays = getSelectedDays();
//            selectedDaysToMin(selectedDays, hour, minute);
//
//            final int deltaNext = findDeltaWithNext(selectedDays, currentTimeInMinuteOfWeek);
//            if (deltaNext >= 0) {
//                delta = deltaNext;
//            } else {
//                final int deltaPrevious = findDeltaWithFirst(selectedDays, currentTimeInMinuteOfWeek);
//                if (deltaPrevious >= 0) {
//                    delta = deltaPrevious;
//                } else {
//                    delta = -1;
//                }
//            }
//        }

        final int currentTimeInMinuteOfWeek = dateToMin(currentDayOfWeek, currentHour, currentMinute);
        List<Integer> selectedDays = getSelectedDays();
        selectedDaysToMin(selectedDays, hour, minute);

        final int deltaNext = findDeltaWithNext(selectedDays, currentTimeInMinuteOfWeek);
        if (deltaNext >= 0) {
            delta = deltaNext;
        } else {
            final int deltaPrevious = findDeltaWithFirst(selectedDays, currentTimeInMinuteOfWeek);
            if (deltaPrevious >= 0) {
                delta = deltaPrevious;
            } else {
                delta = -1;
            }
        }

        updateLeftTimeText(delta);
    }

    private int findDeltaWithFirst(List<Integer> list, int reference){
        if (list.isEmpty() || list == null)
            return -1;

        int min = Collections.min(list);
        int MINUTE_IN_WEEK = MINUTE_IN_DAY * 7;
        if (min < reference)
            return min + (MINUTE_IN_WEEK - reference);

        return -1;
    }

    // List must be a sorted
    private int findDeltaWithNext(List<Integer> list, int reference){
        for (int value : list){
            if (value >= reference)
                return value - reference;
        }

        return -1;
    }

    private int dateToMin(int dayOfWeek, int hour, int minute){
        return dayToNum(dayOfWeek) * MINUTE_IN_DAY + (hour * MINUTE_IN_HOUR + minute);
    }

    private void selectedDaysToMin(List<Integer> days, int hour, int minute){
        for (int i = 0; i < days.size(); i++){
            days.set(i, dateToMin(days.get(i), hour, minute));
        }
    }

    private List<Integer> getSelectedDays(){
        List<Integer> list = new ArrayList<>();

        if (stateMonday.get())
            list.add(Calendar.MONDAY);

        if (stateTuesday.get())
            list.add(Calendar.TUESDAY);

        if (stateWednesday.get())
            list.add(Calendar.WEDNESDAY);

        if (stateThursday.get())
            list.add(Calendar.THURSDAY);

        if (stateFriday.get())
            list.add(Calendar.FRIDAY);

        if (stateSaturday.get())
            list.add(Calendar.SATURDAY);

        if (stateSunday.get())
            list.add(Calendar.SUNDAY);

        return list;
    }

    private void setSelectedDays(List<Integer> list){

        for (int day: list){
            switch (day){
                case Calendar.MONDAY:
                    stateMonday.set(true);
                    break;
                case Calendar.TUESDAY:
                    stateTuesday.set(true);
                    break;
                case Calendar.WEDNESDAY:
                    stateWednesday.set(true);
                    break;
                case Calendar.THURSDAY:
                    stateThursday.set(true);
                    break;
                case Calendar.FRIDAY:
                    stateFriday.set(true);
                    break;
                case Calendar.SATURDAY:
                    stateSaturday.set(true);
                    break;
                case Calendar.SUNDAY:
                    stateSunday.set(true);
                    break;
            }
        }
    }

    private int dayToNum(int day){
        switch (day) {
            case Calendar.MONDAY:
                return 0;
            case Calendar.TUESDAY:
                return 1;
            case Calendar.WEDNESDAY:
                return 2;
            case Calendar.THURSDAY:
                return 3;
            case Calendar.FRIDAY:
                return 4;
            case Calendar.SATURDAY:
                return 5;
            case Calendar.SUNDAY:
                return 6;
        }
        return 0;
    }

    private void updateLeftTimeText(int minute){
        if (minute < 0){
            leftTimeText.set("");
            return;
        }

        int min = minute % MINUTE_IN_HOUR;
        int buf = minute / MINUTE_IN_HOUR;

        int hour = buf % HOUR_IN_DAY;
        buf /= HOUR_IN_DAY;

        int day = buf;

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(getApplication().getString(R.string.schedule_in_a)).append(" ");
        if (day != 0)
            stringBuilder.append(day).append(" ").append(getApplication().getString(R.string.schedule_day)).append(" ");
        stringBuilder.append(hour).append(" ").append(getApplication().getString(R.string.schedule_hour)).append(" ");
        stringBuilder.append(min).append(" ").append(getApplication().getString(R.string.schedule_minute));

        leftTimeText.set(stringBuilder.toString());
    }



}