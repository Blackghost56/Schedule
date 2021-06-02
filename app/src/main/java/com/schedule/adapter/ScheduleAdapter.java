package com.schedule.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.schedule.R;
import com.schedule.Schedule;
import com.schedule.Task;
import com.schedule.databinding.ItemScheduleBinding;
import com.schedule.model.TaskModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    final private String TAG = ScheduleAdapter.class.getSimpleName();

    protected List<TaskModel> itemsList = new ArrayList<>();
    protected Context context;

    public static int UNCHECKED = -1;
    protected int selectedPosition = UNCHECKED;



    public ScheduleAdapter(Context context, LiveData<List<Task>> itemsList, int defaultPosition){
        this.context = context;

        itemsList.observe((LifecycleOwner) context, itemsList1 -> {
            if (itemsList1 != null) {
                this.itemsList.clear();
                for (Task item : itemsList1) {
                    TaskModel itemModel = new TaskModel(context, item);
                    this.itemsList.add(itemModel);
                }

                setSelected(defaultPosition);
            }
            notifyDataSetChanged();
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemScheduleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_schedule, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ScheduleAdapter.ViewHolder) holder).bind(itemsList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void setSelected(int position) {
//        if (selectedPosition != UNCHECKED && !itemsList.isEmpty()) {
//            TM itemModelOld = itemsList.get(selectedPosition);
//            if (itemModelOld != null)
//                itemModelOld.setSelected(false);
//        }
//
//        T item = null;
//        if (position != UNCHECKED){
//            TM itemModel = itemsList.get(position);
//            if (itemModel != null) {
//                itemModel.setSelected(true);
//                item = itemModel.getItem();
//            }
//        }
//
//        selectedPosition = position;
//        notifyDataSetChanged();
//        checkedCallback(item);
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemScheduleBinding binding;

        public ViewHolder(ItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TaskModel model){
            binding.setViewModel(model);
            binding.executePendingBindings();

//            itemView.setOnClickListener(v -> {
//                if (checkedId != model.getId()) {
//                    setSelected(model.getId());
//                } else {
//                    setSelected(UNCHECKED);
//                }
//            });

        }

    }
}
