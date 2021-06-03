package com.schedule.adapter;

import android.content.Context;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.schedule.R;
import com.schedule.Task;
import com.schedule.databinding.ItemScheduleBinding;
import com.schedule.model.TaskModel;

import java.util.ArrayList;
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
        if (selectedPosition != UNCHECKED && !itemsList.isEmpty()) {
            TaskModel itemModelOld = itemsList.get(selectedPosition);
            if (itemModelOld != null)
                itemModelOld.setSelected(false);
        }

        Task item = null;
        if (position != UNCHECKED){
            TaskModel itemModel = itemsList.get(position);
            if (itemModel != null) {
                itemModel.setSelected(true);
                item = itemModel.getItem();
            }
        }

        selectedPosition = position;
        notifyDataSetChanged();
        onClickCallback(item);
    }


    private RecyclerView recyclerView = null;
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = ViewHolder.class.getSimpleName();

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

            itemView.setOnClickListener(v -> {
//                Task task = binding.getViewModel().getItem();
//                onClickCallback(task);

                if (selectedPosition != getAdapterPosition()) {
                    setSelected(getAdapterPosition());
                } else {
                    setSelected(UNCHECKED);
                }

                TransitionManager.beginDelayedTransition(recyclerView);

                Log.d(TAG, "setOnClickListener");
            });

            itemView.setOnLongClickListener(v -> {
                Log.d(TAG, "setOnLongClickListener");

//                Task task = binding.getViewModel().getItem();
//                onLongClickCallback(task);

//                if (selectedPosition != getAdapterPosition()) {
//                    setSelected(getAdapterPosition());
//                } else {
//                    setSelected(UNCHECKED);
//                }

                return true;
            });
        }



    }


    public interface Callback<T> {
        void onClick(T item);
        void onLongClick(T item);
    }

    private Callback<Task> callback;
    public void registerCallback(Callback<Task> callback){
        this.callback = callback;
    }

    protected void onClickCallback(Task item){
        if (callback != null)
            callback.onClick(item);
    }

    protected void onLongClickCallback(Task item){
        if (callback != null)
            callback.onLongClick(item);
    }

}
