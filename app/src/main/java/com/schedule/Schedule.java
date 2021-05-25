package com.schedule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.schedule.databinding.ActivityScheduleBinding;
import com.schedule.model.ScheduleViewModel;

public class Schedule extends AppCompatActivity {


    private ScheduleViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        ActivityScheduleBinding dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_schedule);
        dataBinding.setViewModel(viewModel);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, NewTask.newInstance()).addToBackStack(null).commit();
            }
        });

    }
}