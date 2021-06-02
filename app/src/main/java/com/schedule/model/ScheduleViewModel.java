package com.schedule.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.schedule.fragment.NewTaskFragment;
import com.schedule.Task;
import com.schedule.tools.SingleLiveEvent;

import java.util.ArrayList;
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

    public void onFABPressed(){
        actionOpenFragment.setValue(NewTaskFragment.newInstance());
    }

}
