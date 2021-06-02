package com.schedule.model;

import androidx.databinding.ObservableBoolean;

public class ItemModel<T> {

    public ObservableBoolean isSelected = new ObservableBoolean(false);
    protected final T item;
    protected long id;

    public ItemModel(T item){
        this.item = item;
    }

    public long getId(){
        return id;
    }
    public T getItem() {
        return item;
    }
    public void setSelected(boolean selected) {
        isSelected.set(selected);
    }
    public boolean isSelected() {
        return isSelected.get();
    }
}
