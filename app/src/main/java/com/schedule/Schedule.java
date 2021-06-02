package com.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.schedule.model.ScheduleViewModel;

public class Schedule extends AppCompatActivity {

    private final String TAG = Schedule.class.getSimpleName();

    private ScheduleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_schedule);
        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow();
        }

        // ToDo not working!?
//        viewModel.getActionOpenFragment().observe(this, fragment -> {
//            Log.d(TAG, "getActionOpenFragment");
//            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//        });
    }

}