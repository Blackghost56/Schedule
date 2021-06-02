package com.schedule;

import android.annotation.SuppressLint;
import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

// todo extends Enity
public class Task {


    public static final int MINUTE_IN_HOUR = 60;
    public static final int HOUR_IN_DAY = 24;
    public static final int DAY_IN_WEEK = 7;
    public static final int MINUTE_IN_DAY = MINUTE_IN_HOUR * HOUR_IN_DAY;
    public static final int MINUTE_IN_WEEK = MINUTE_IN_DAY * DAY_IN_WEEK;


    public Task(){
        isEnabled = false;
        timeOfDay = new TimeOfDay();
        daysOfWeek = new TreeSet<>();
        repeat = false;
    }

    public boolean isEnabled;
    public TimeOfDay timeOfDay;
    public Set<DayOfWeek> daysOfWeek;
    public boolean repeat;

    public String repeatToStr(Context context){
        if (repeat) {
            return context.getString(R.string.schedule_repeat_weekly);
        } else {
            return context.getString(R.string.schedule_repeat_once);
        }
    }

    public String daysOfWeekToStr(Context context){
        if (daysOfWeek == null)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (DayOfWeek day: daysOfWeek){
            switch (day) {
                case MONDAY:
                stringBuilder.append(context.getString(R.string.schedule_repeat_monday_s)).append(" ");
                break;
                case TUESDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_tuesday_s)).append(" ");
                    break;
                case WEDNESDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_wednesday_s)).append(" ");
                    break;
                case THURSDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_thursday_s)).append(" ");
                    break;
                case FRIDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_friday_s)).append(" ");
                    break;
                case SATURDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_saturday_s)).append(" ");
                    break;
                case SUNDAY:
                    stringBuilder.append(context.getString(R.string.schedule_repeat_sunday_s));
                    break;
            }
        }

        return stringBuilder.toString();
    }


    @SuppressLint("DefaultLocale")
    public String timeToStr(Context context){
        if (timeOfDay == null)
            return "";

        return String.format("%02d:%02d", timeOfDay.getHour(), timeOfDay.getMinute());
    }


    public int leftTime() throws Exception {
        int leftTime = 0;

        Calendar calendar = GregorianCalendar.getInstance();
        final int currentTimeInMinuteOfWeek = dateToMin(dayOfWeekConvert(calendar.get(Calendar.DAY_OF_WEEK)), calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));

        List<Integer> list = selectedDaysToMin();

        final int deltaNext = findDeltaWithNext(list, currentTimeInMinuteOfWeek);
        if (deltaNext >= 0) {
            leftTime = deltaNext;
        } else {
            final int deltaPrevious = findDeltaWithFirst(list, currentTimeInMinuteOfWeek);
            if (deltaPrevious >= 0) {
                leftTime = deltaPrevious;
            } else {
                leftTime = -1;
            }
        }

        return leftTime;
    }

    public String leftTimeStr(Context context){
        int leftTime;
        try {
            leftTime = leftTime();
        } catch (Exception e){
            e.printStackTrace();
            return "";
        }

        if (leftTime < 0 || leftTime > MINUTE_IN_WEEK)
            return "";

        int min = leftTime % MINUTE_IN_HOUR;
        int buf = leftTime / MINUTE_IN_HOUR;

        int hour = buf % HOUR_IN_DAY;
        buf /= HOUR_IN_DAY;

        int day = buf;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getString(R.string.schedule_in_a)).append(" ");
        if (day != 0)
            stringBuilder.append(day).append(" ").append(context.getString(R.string.schedule_day)).append(" ");
        stringBuilder.append(hour).append(" ").append(context.getString(R.string.schedule_hour)).append(" ");
        stringBuilder.append(min).append(" ").append(context.getString(R.string.schedule_minute));

        return stringBuilder.toString();
    }


    private List<Integer> selectedDaysToMin(){
        List<Integer> list = new ArrayList<>();
        for (DayOfWeek day: daysOfWeek){
            list.add(dateToMin(day, timeOfDay.getHour(), timeOfDay.getMinute()));
        }

        return list;
    }

    public static DayOfWeek dayOfWeekConvert(int calendarDay) throws Exception {
        switch (calendarDay) {
            case Calendar.MONDAY:
                return DayOfWeek.MONDAY;
            case Calendar.TUESDAY:
                return DayOfWeek.TUESDAY;
            case Calendar.WEDNESDAY:
                return DayOfWeek.WEDNESDAY;
            case Calendar.THURSDAY:
                return DayOfWeek.THURSDAY;
            case Calendar.FRIDAY:
                return DayOfWeek.FRIDAY;
            case Calendar.SATURDAY:
                return DayOfWeek.SATURDAY;
            case Calendar.SUNDAY:
                return DayOfWeek.SUNDAY;
            default:
                throw new Exception();
        }
    }



    private int dateToMin(DayOfWeek dayOfWeek, int hour, int minute) {
        return dayOfWeek.getValue() * MINUTE_IN_DAY + (hour * MINUTE_IN_HOUR + minute);
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


    public enum  DayOfWeek {
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);

        private final int value;

        DayOfWeek(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }

    public static class TimeOfDay {
        private int value = 0;          // In minute

        public void setValue(int value) throws Exception {
            if (value < 0 || value > MINUTE_IN_DAY)
                throw new Exception("value < 0 || value > MINUTE_IN_DAY");
            this.value = value;
        }

        public int getValue(){
            return value;
        }

        public void setValueHM(int hour, int minute) throws Exception {
            value = hour * MINUTE_IN_HOUR + minute;

            if (value < 0 || value > MINUTE_IN_DAY)
                throw new Exception("value < 0 || value > MINUTE_IN_DAY");
        }

        public int getHour(){
            return value / MINUTE_IN_HOUR;
        }

        public int getMinute(){
            return value % MINUTE_IN_HOUR;
        }
    }

}
