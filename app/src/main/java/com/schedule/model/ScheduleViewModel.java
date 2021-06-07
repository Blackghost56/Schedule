package com.schedule.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.schedule.adapter.ScheduleAdapter;
import com.schedule.fragment.NewTaskFragment;
import com.schedule.Task;
import com.schedule.tools.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleViewModel extends AndroidViewModel {

//    private final String TAG = ScheduleViewModel.class.getSimpleName();

    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        // ToDo load from device
        try {
            Task task = new Task();
            task.isEnabled = true;
            task.timeOfDay.setValueHM(12, 34);
            task.daysOfWeek.add(Task.DayOfWeek.FRIDAY);
            task.repeat = true;
            TaskModelForAdapter taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
            itemList.add(taskModel);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(123);
            task.daysOfWeek.add(Task.DayOfWeek.MONDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
            itemList.add(taskModel);

            task = new Task();
            task.isEnabled = false;
            task.timeOfDay.setValue(542);
            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
            task.repeat = false;
            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
            itemList.add(taskModel);

//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
//
//            task = new Task();
//            task.isEnabled = false;
//            task.timeOfDay.setValue(542);
//            task.daysOfWeek.add(Task.DayOfWeek.SATURDAY);
//            task.daysOfWeek.add(Task.DayOfWeek.THURSDAY);
//            task.repeat = false;
//            taskModel = new TaskModelForAdapter(getApplication().getApplicationContext(), task);
//            itemList.add(taskModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ScheduleAdapter.Mode mode = ScheduleAdapter.Mode.SINGLE_SELECT;
    public ScheduleAdapter.Mode getMode() {
        return mode;
    }

    public void onModeChanged(ScheduleAdapter.Mode mode){
        this.mode = mode;
    }


    private final List<TaskModelForAdapter> itemList = new ArrayList<>();
    public List<TaskModelForAdapter> getItemList(){
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

//    public void onItemsRemoved(List<TaskModelForAdapter> removedItems){
//        Log.d(TAG, "onItemsRemoved, count: " + removedItems.size());
//        for (TaskModelForAdapter model: removedItems){
//            Log.d(TAG, model.getTask().timeToStr(getApplication().getApplicationContext()));
//            Log.d(TAG, "----------");
//        }
//    }


    private TaskModelForNT taskModelForNT;
    public TaskModelForNT getTaskModelForNT(){
        return taskModelForNT;
    }

    public void onCreatePressedNT(){
        Task task = taskModelForNT.getTask();
        task.isEnabled = true;

        itemList.add(new TaskModelForAdapter(getApplication().getApplicationContext(), task));

        actionPopBackStack.call();
    }


    @Override
    protected void onCleared() {
        super.onCleared();

        // ToDo save data to device
    }


}
