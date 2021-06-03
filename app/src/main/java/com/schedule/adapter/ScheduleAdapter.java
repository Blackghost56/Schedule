package com.schedule.adapter;

import android.content.Context;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
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

                //setSelected(defaultPosition);
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
        // turn off the last selection
        if (selectedPosition != UNCHECKED && !itemsList.isEmpty()) {
            TaskModel itemModelOld = itemsList.get(selectedPosition);
            if (itemModelOld != null)
                itemModelOld.setExpanded(false);
        }

        // turn on the new selection
        Task item = null;
        if (position != UNCHECKED){
            TaskModel itemModel = itemsList.get(position);
            if (itemModel != null) {
                itemModel.setExpanded(true);
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

//            SwitchCompat switchCompat = itemView.findViewById(R.id.switchEnable);
//            switchCompat.setThumbDrawable();
//            switchCompat.setTrackDrawable();

            itemView.setOnClickListener(v -> {
                Log.d(TAG, "setOnClickListener");
//                Task task = binding.getViewModel().getItem();
//                onClickCallback(task);

//                if (selectedPosition != getAdapterPosition()) {
//                    setSelected(getAdapterPosition());
//                } else {
//                    setSelected(UNCHECKED);
//                }

                switch (mode){
                    case SINGLE_SELECT:
                        if (selectedPosition != getAdapterPosition()) {
                            setSelected(getAdapterPosition());
                        } else {
                            setSelected(UNCHECKED);
                        }
                        TransitionManager.beginDelayedTransition(recyclerView);
                        break;
                    case MULTI_SELECT:
                        model.setSelected(!model.isSelected());
                        notifyDataSetChanged();
                        break;
                }
                //notifyItemChanged(getAdapterPosition());
            });

            itemView.setOnLongClickListener(v -> {
                Log.d(TAG, "setOnLongClickListener");


                toggleMode();

                switch (mode){
                    case SINGLE_SELECT:
                        // Unselect all item
                        for (TaskModel taskModel: itemsList){
                            taskModel.setSelected(false);
                        }
//                        model.setSelected(false);
                        break;
                    case MULTI_SELECT:
                        // Collapse all item
                        for (TaskModel taskModel: itemsList){
                            taskModel.setExpanded(false);


                        }
                        TransitionManager.beginDelayedTransition(recyclerView);
                        model.setSelected(true);
//                        model.setSelected(!model.isSelected());
                        break;
                }
                //model.setSelected(!model.isSelected());
//                if (model.isSelected()){
//                    model.setExpanded(false);
//                }

                notifyDataSetChanged();
                //notifyItemChanged(getAdapterPosition());

                return true;
            });
        }

    }

    private enum Mode {SINGLE_SELECT, MULTI_SELECT}
    private Mode mode = Mode.SINGLE_SELECT;
    private void toggleMode(){
        switch (mode){
            case SINGLE_SELECT:
                mode = Mode.MULTI_SELECT;


                break;
            case MULTI_SELECT:
                mode = Mode.SINGLE_SELECT;


                break;
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
