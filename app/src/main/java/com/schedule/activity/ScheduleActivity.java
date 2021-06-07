package com.schedule.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.schedule.R;
import com.schedule.fragment.MainFragment;

public class ScheduleActivity extends AppCompatActivity {

//    private final String TAG = ScheduleActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, MainFragment.newInstance()).commitNow();
        }
    }


    public interface BackPressed {
        boolean onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof BackPressed) || !((BackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}