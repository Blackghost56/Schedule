package com.schedule.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schedule.R;
import com.schedule.databinding.FragmentNewTaskBinding;
import com.schedule.databinding.FragmentNewTaskLowScreenBinding;
import com.schedule.model.ScheduleViewModel;

public class NewTaskFragment extends Fragment {

    private ViewDataBinding binding;

    public static NewTaskFragment newInstance() {
        return new NewTaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (!isLowScreen()) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false);
        } else {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task_low_screen, container, false);
        }

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ScheduleViewModel viewModel = new ViewModelProvider(requireActivity()).get(ScheduleViewModel.class);
        if (binding instanceof FragmentNewTaskBinding) {
            ((FragmentNewTaskBinding)binding).setViewModel(viewModel);
            ((FragmentNewTaskBinding)binding).setTaskModel(viewModel.getTaskModelForNT());
        } else if (binding instanceof FragmentNewTaskLowScreenBinding) {
            ((FragmentNewTaskLowScreenBinding)binding).setViewModel(viewModel);
            ((FragmentNewTaskLowScreenBinding)binding).setTaskModel(viewModel.getTaskModelForNT());
        }

        viewModel.getActionPopBackStack().observe(getViewLifecycleOwner(), aVoid -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    protected final int LOW_SCREEN_DP = 500;
    protected boolean isLowScreen(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        return dpHeight < LOW_SCREEN_DP;
    }
    protected final int WIDE_SCREEN_DP = 700;
    protected boolean isWideScreen(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return dpWidth > WIDE_SCREEN_DP;
    }
}