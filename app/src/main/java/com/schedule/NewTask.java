package com.schedule;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.schedule.databinding.FragmentNewTaskBinding;
import com.schedule.model.NewTaskViewModel;
import com.schedule.model.ScheduleViewModel;

import java.util.Objects;

public class NewTask extends Fragment {

    private NewTaskViewModel viewModel;
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

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);
//
//        binding.setViewModel(viewModel);
//    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NewTaskViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getActionPopBackStack().observe(getViewLifecycleOwner(), aVoid -> requireActivity().getSupportFragmentManager().popBackStack());
    }
}