package com.schedule;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schedule.databinding.FragmentNewTaskBinding;
import com.schedule.model.NewTaskViewModel;

public class NewTask extends Fragment {

    private NewTaskViewModel mViewModel;
    private FragmentNewTaskBinding binding;

    public static NewTask newInstance() {
        return new NewTask();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_new_task, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);

        binding.setViewModel(mViewModel);
    }

}