package com.schedule.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schedule.R;
import com.schedule.databinding.FragmentNewTaskBinding;
import com.schedule.model.NewTaskViewModel;

public class NewTaskFragment extends Fragment {

    private FragmentNewTaskBinding binding;

    public static NewTaskFragment newInstance() {
        return new NewTaskFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_task, container, false);

        return binding.getRoot();
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NewTaskViewModel viewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);
        binding.setViewModel(viewModel);
        binding.setTaskModel(viewModel.getTaskModel());

        viewModel.getActionPopBackStack().observe(getViewLifecycleOwner(), aVoid -> requireActivity().getSupportFragmentManager().popBackStack());
    }
}