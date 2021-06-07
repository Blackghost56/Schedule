package com.schedule.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.schedule.fragment.NewTaskFragment;
import com.schedule.Task;
import com.schedule.tools.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

    private final String TAG = ScheduleViewModel.class.getSimpleName();

    public ScheduleViewModel(@NonNull Application application) {
        super(application);


        List<Task> list = new ArrayList<>();
        try {
            Task task = new Task();
            task.isEnabled = true;
            task.timeOfDay.setValueHM(12, 34);
            task.daysOfWeek.add(Task.DayOfWeek.FRIDAY);
            task.repeat = true;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(123);
            task.daysOfWeek.add(Task.DayOfWeek.MONDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            list.add(task);
        } catch (Exception e) {
            e.printStackTrace();
        }

        itemList.setValue(list);
    }

    private MutableLiveData<List<Task>> itemList = new MutableLiveData<>();
    public LiveData<List<Task>> getItemList(){
        return itemList;
    }

    private final SingleLiveEvent<Fragment> actionOpenFragment = new SingleLiveEvent<>();
    public LiveData<Fragment> getActionOpenFragment(){
        return actionOpenFragment;
    }

    private final SingleLiveEvent<Void> actionDelete = new SingleLiveEvent<>();
    public LiveData<Void> getActionDelete(){
        return actionDelete;
    }

    protected final SingleLiveEvent<Void> actionPopBackStack = new SingleLiveEvent<>();
    public LiveData<Void> getActionPopBackStack(){
        return actionPopBackStack;
    }

    public void onFABCreatePressed(){
        Task task = new Task();
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            task.timeOfDay.setValueHM(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            task.daysOfWeek.add(Task.dayOfWeekConvert(calendar.get(Calendar.DAY_OF_WEEK)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        taskModelForNT = new TaskModelForNT(getApplication().getApplicationContext(), task);
        actionOpenFragment.setValue(NewTaskFragment.newInstance());
    }

    public void onFABDeletePressed(){
        actionDelete.call();
    }


    private TaskModelForNT taskModelForNT;
    public TaskModelForNT getTaskModelForNT(){
        return taskModelForNT;
    }

    public void onCreatePressedNT(){
        Task task = taskModelForNT.getTask();
        task.isEnabled = true;

        List<Task> list = itemList.getValue();
        list.add(task);
        itemList.setValue(list);

        actionPopBackStack.call();
    }


}
