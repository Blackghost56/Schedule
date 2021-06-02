package com.schedule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.schedule.R;
import com.schedule.fragment.MainFragment;
import com.schedule.model.ScheduleViewModel;

public class ScheduleActivity extends AppCompatActivity {

    private final String TAG = ScheduleActivity.class.getSimpleName();

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