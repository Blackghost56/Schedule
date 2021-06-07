package com.schedule.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.schedule.R;
import com.schedule.Task;
import com.schedule.adapter.ScheduleAdapter;
import com.schedule.databinding.FragmentMainBinding;
import com.schedule.model.ScheduleViewModel;

public class MainFragment extends Fragment {

    private final String TAG = MainFragment.class.getSimpleName();

    //todo
//    protected final String SELECTED_ID = "selected_id";

    private ScheduleViewModel viewModel;
    private FragmentMainBinding binding;
    private RecyclerView recyclerView;
    protected ScheduleAdapter adapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        binding.setViewModel(viewModel);


        recyclerView = requireActivity().findViewById(R.id.itemList);
//        // Removes blinks
//        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);

        adapter = new ScheduleAdapter(requireContext(), viewModel.getItemList(), -1);
        adapter.registerCallback(new ScheduleAdapter.Callback<Task>() {
            @Override
            public void onClick(Task item) {

            }

            @Override
            public void onLongClick(Task item) {

            }

            @Override
            public void modeChanged(ScheduleAdapter.Mode mode) {
                FloatingActionButton fabDelete = getActivity().findViewById(R.id.fabDelete);
                FloatingActionButton fabCreate = getActivity().findViewById(R.id.fabCreate);
                Transition transition = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition((ViewGroup) fabDelete.getParent(), transition);

                switch (mode){
                    case SINGLE_SELECT:
                        fabDelete.setVisibility(View.GONE);
                        fabCreate.setVisibility(View.VISIBLE);
                        break;
                    case MULTI_SELECT:
                        fabCreate.setVisibility(View.GONE);
                        fabDelete.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        recyclerView.setAdapter(adapter);


        viewModel.getActionOpenFragment().observe(getViewLifecycleOwner(), fragment -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
        });

    }
}