package com.schedule.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.transition.Fade;
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
import com.schedule.dialog.AreYouSureDialog;
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
//        return inflater.inflate(R.layout.fragment_main, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        return binding.getRoot();
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
//        binding.setViewModel(viewModel);
//
//
//
////        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
////
////        fab.setOnClickListener(v -> {
////            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, NewTask.newInstance()).addToBackStack(null).commit();
////        });
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
                FloatingActionButton floatingActionButton = getActivity().findViewById(R.id.floatingActionButton);
                FloatingActionButton fab = getActivity().findViewById(R.id.fab);
                Transition transition = new Slide(Gravity.BOTTOM);
                TransitionManager.beginDelayedTransition((ViewGroup) floatingActionButton.getParent(), transition);

                switch (mode){
                    case SINGLE_SELECT:
                        floatingActionButton.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);
                        break;
                    case MULTI_SELECT:
                        fab.setVisibility(View.GONE);
                        floatingActionButton.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        recyclerView.setAdapter(adapter);


        viewModel.getActionOpenFragment().observe(getViewLifecycleOwner(), fragment -> {
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
//            requireActivity().getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.container, fragment).addToBackStack(null).commit();

        });

//        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
//        fab.setOnClickListener(v -> {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, NewTask.newInstance()).addToBackStack(null).commit();
//        });
    }
}