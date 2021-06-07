package com.schedule.adapter;

import android.content.Context;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
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
import com.schedule.model.TaskModelForList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    final private String TAG = ScheduleAdapter.class.getSimpleName();

    protected List<TaskModelForList> itemsList = new ArrayList<>();
    protected Context context;

    public static int UNCHECKED = -1;
    protected int selectedPosition = UNCHECKED;

//    protected List<Integer> selectedItems = new ArrayList<>();
//    protected Set<Integer> selectedItems = new HashSet<>();


    public ScheduleAdapter(Context context, LiveData<List<Task>> itemsList, int defaultPosition){
        this.context = context;

        itemsList.observe((LifecycleOwner) context, itemsList1 -> {
            if (itemsList1 != null) {
                this.itemsList.clear();
                for (Task item : itemsList1) {
                    TaskModelForList itemModel = new TaskModelForList(context, item);
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
            TaskModelForList itemModelOld = itemsList.get(selectedPosition);
            if (itemModelOld != null) {
                itemModelOld.setExpanded(false);
                notifyItemChanged(selectedPosition);
            }
        }

        // turn on the new selection
        Task item = null;
        if (position != UNCHECKED){
            TaskModelForList itemModel = itemsList.get(position);
            if (itemModel != null) {
                itemModel.setExpanded(true);
                item = itemModel.getTask();
            }
        }

        selectedPosition = position;
        notifyItemChanged(selectedPosition);
        onClickCallback(item);
    }


    private RecyclerView recyclerView = null;
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    public void removeSelectedItems(){
        if (mode == Mode.MULTI_SELECT) {
            for (Iterator<TaskModelForList> it = itemsList.iterator(); it.hasNext(); ) {
                TaskModelForList model = it.next();
                if (model.isSelected())
                    it.remove();
            }
            notifyDataSetChanged();
            toggleMode();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = ViewHolder.class.getSimpleName();

        public ItemScheduleBinding binding;

        public ViewHolder(ItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TaskModelForList model){
            binding.setViewModel(model);
            binding.executePendingBindings();

            itemView.setOnClickListener(v -> {
                switch (mode){
                    case SINGLE_SELECT:
                        if (selectedPosition != getAdapterPosition()) {
                            setSelected(getAdapterPosition());
                        } else {
                            setSelected(UNCHECKED);
                        }
                        break;
                    case MULTI_SELECT:
//                        if (model.isSelected()){
//                            model.setSelected(false);
////                            for (Iterator<Integer> it = selectedItems.iterator(); it.hasNext();)
////                                if (it.next() == getAdapterPosition())
////                                    it.remove();
//                        } else {
//                            model.setSelected(true);
////                            selectedItems.add(getAdapterPosition());
//                        }

                        model.setSelected(!model.isSelected());
//                        selectedItems.add(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                        break;
                }
            });

            itemView.setOnLongClickListener(v -> {
                toggleMode();

                if (mode == Mode.MULTI_SELECT)
                    model.setSelected(true);

                return true;
            });
        }

    }

    public enum Mode {SINGLE_SELECT, MULTI_SELECT}
    private Mode mode = Mode.SINGLE_SELECT;
    public void toggleMode(){
        switch (mode){
            case SINGLE_SELECT:
                mode = Mode.MULTI_SELECT;

                // Collapse all item
                for (TaskModelForList taskModel: itemsList){
                    taskModel.setExpanded(false);
                    taskModel.setModeMultiSelect(true);
                }

                selectedPosition = UNCHECKED;
//                selectedItems.clear();

                break;
            case MULTI_SELECT:
                mode = Mode.SINGLE_SELECT;

                // Unselect all item
                for (TaskModelForList taskModel: itemsList){
                    taskModel.setSelected(false);
                    taskModel.setModeMultiSelect(false);
                }
//                selectedItems.clear();

                break;
        }
        Transition transition = new Fade();
        TransitionManager.beginDelayedTransition(recyclerView, transition);
        notifyDataSetChanged();
        onModeChangedCallback(mode);
    }


    public interface Callback<T> {
        void onClick(T item);
        void onLongClick(T item);
        void modeChanged(Mode mode);
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

    protected void onModeChangedCallback(Mode mode){
        if (callback != null)
            callback.modeChanged(mode);
    }

}
