package com.schedule.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.schedule.Task;
import com.schedule.tools.SingleLiveEvent;

import java.util.Calendar;
import java.util.GregorianCalendar;



public class NewTaskViewModel extends AndroidViewModel {

//    final private String TAG = NewTaskViewModel.class.getSimpleName();

    protected final SingleLiveEvent<Void> actionPopBackStack = new SingleLiveEvent<>();
    public LiveData<Void> getActionPopBackStack(){
        return actionPopBackStack;
    }

    public ObservableField<String> leftTimeText = new ObservableField<>("");

    private TaskModel taskModel = null;
    public TaskModel getTaskModel(){
        return taskModel;
    }

    public NewTaskViewModel(@NonNull Application application) {
        super(application);

        Task task = new Task();
        try {
            Calendar calendar = GregorianCalendar.getInstance();
            task.timeOfDay.setValueHM(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
            task.daysOfWeek.add(Task.dayOfWeekConvert(calendar.get(Calendar.DAY_OF_WEEK)));
        } catch (Exception e) {
            e.printStackTrace();
        }

        taskModel = new TaskModel(task) {
            @Override
            public void onNumChange(int hour, int minute) {
                try {
                    task.timeOfDay.setValueHM(hour, minute);
                    updateLeftTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSelectDaysGroupPressed() {
                getSelectedDays();
                updateLeftTime();
            }
        };

        taskModel.updateSelectedDays();
        updateLeftTime();
    }

    public void onCreatePressed(){
        taskModel.getTask().isEnabled = true;
        actionPopBackStack.call();
    }


    private void updateLeftTime(){
        leftTimeText.set(taskModel.getTask().leftTimeStr(getApplication().getApplicationContext()));
    }


}