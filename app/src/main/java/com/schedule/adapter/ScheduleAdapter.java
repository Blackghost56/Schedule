package com.schedule.adapter;

import android.content.Context;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.schedule.R;
import com.schedule.databinding.ItemScheduleBinding;
import com.schedule.model.TaskModelForAdapter;

import java.util.Iterator;
import java.util.List;


public class ScheduleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

//    final private String TAG = ScheduleAdapter.class.getSimpleName();

    protected List<TaskModelForAdapter> itemsList;
    protected Context context;

    public static int ALL_MINIMIZED = -1;
    protected int expandedPosition = ALL_MINIMIZED;


    public ScheduleAdapter(Context context, List<TaskModelForAdapter> itemsList, Mode mode){
        this.context = context;
        this.itemsList = itemsList;
        this.mode = mode;
        
        int expandedPosition = 0;
        for(TaskModelForAdapter model: itemsList){
            if (model.isExpanded()) {
                this.expandedPosition = expandedPosition;
                break;
            }
            expandedPosition++;
        }
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
        if (expandedPosition != ALL_MINIMIZED && !itemsList.isEmpty()) {
            TaskModelForAdapter itemModelOld = itemsList.get(expandedPosition);
            if (itemModelOld != null) {
                itemModelOld.setExpanded(false);
                notifyItemChanged(expandedPosition);
            }
        }

        // turn on the new selection
        if (position != ALL_MINIMIZED){
            TaskModelForAdapter itemModel = itemsList.get(position);
            if (itemModel != null) {
                itemModel.setExpanded(true);
            }
        }

        expandedPosition = position;
        notifyItemChanged(expandedPosition);
    }


    private RecyclerView recyclerView = null;
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

    public void removeSelectedItems(){
        if (mode == Mode.MULTI_SELECT) {
//            List<TaskModelForAdapter> removedItems = new ArrayList<>();
            for (Iterator<TaskModelForAdapter> it = itemsList.iterator(); it.hasNext(); ) {
                TaskModelForAdapter model = it.next();
                if (model.isSelected()) {
//                    removedItems.add(model);
                    it.remove();
                }
            }
//            onItemsRemovedCallback(removedItems);
            notifyDataSetChanged();
            toggleMode();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        private final String TAG = ViewHolder.class.getSimpleName();

        public ItemScheduleBinding binding;

        public ViewHolder(ItemScheduleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TaskModelForAdapter model){
            binding.setViewModel(model);
            binding.executePendingBindings();

            itemView.setOnClickListener(v -> {
                switch (mode){
                    case SINGLE_SELECT:
                        if (expandedPosition != getAdapterPosition()) {
                            setSelected(getAdapterPosition());
                        } else {
                            setSelected(ALL_MINIMIZED);
                        }
                        break;
                    case MULTI_SELECT:
                        model.setSelected(!model.isSelected());
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
    private Mode mode;
    public Mode getMode(){
        return mode;
    }
    public void toggleMode(){
        switch (mode){
            case SINGLE_SELECT:
                mode = Mode.MULTI_SELECT;

                // Collapse all item
                for (TaskModelForAdapter taskModel: itemsList){
                    taskModel.setExpanded(false);
                    taskModel.setModeMultiSelect(true);
                }
                expandedPosition = ALL_MINIMIZED;
                break;
            case MULTI_SELECT:
                mode = Mode.SINGLE_SELECT;

                // Unselect all item
                for (TaskModelForAdapter taskModel: itemsList){
                    taskModel.setSelected(false);
                    taskModel.setModeMultiSelect(false);
                }
                break;
        }
        Transition transition = new Fade();
        TransitionManager.beginDelayedTransition(recyclerView, transition);
        notifyDataSetChanged();
        onModeChangedCallback(mode);
    }


    public interface Callback {
        void onModeChanged(Mode mode);
//        void onItemsRemoved(List<TaskModelForAdapter> removedItems);
    }

    private Callback callback;
    public void registerCallback(Callback callback){
        this.callback = callback;
    }

    protected void onModeChangedCallback(Mode mode){
        if (callback != null)
            callback.onModeChanged(mode);
    }

//    protected void onItemsRemovedCallback(List<TaskModelForAdapter> removedItems){
//        if (callback != null)
//            callback.onItemsRemoved(removedItems);
//    }

}
