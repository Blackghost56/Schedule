package com.schedule.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;

import com.schedule.R;

public class TimePicker extends FrameLayout {

    private final String TAG = TimePicker.class.getSimpleName();

    // ui components
    private final NumberPicker hourPicker;
    private final NumberPicker minutePicker;

    // callbacks
    private OnTimeChangedListener onTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param hour The current hour.
         * @param minute The current minute.
         */
        void onTimeChanged(TimePicker view, int hour, int minute);
    }

    @SuppressLint("DefaultLocale")
    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER = value -> {
                return String.format("%02d", value);
            };

    public TimePicker(@NonNull Context context) {
        this(context, null);
    }

    public TimePicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TimePicker(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_time_picker, this, true);

        hourPicker = findViewById(R.id.hour);
        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        hourPicker.setFormatter(TWO_DIGIT_FORMATTER);
        hourPicker.setOnValueChangedListener((spinner, oldVal, newVal) -> {
            onTimeChanged();
        });

        minutePicker = findViewById(R.id.minute);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        minutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        minutePicker.setOnValueChangedListener((spinner, oldVal, newVal) -> {
            onTimeChanged();
        });

//        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TimePicker, defStyleAttr, defStyleRes);
//
//        int value;
//        try {
////            this.minValue = attributes.getInt(R.styleable.TimePicker_minValue, 0);
////            this.maxValue = attributes.getInt(R.styleable.TimePicker_maxValue, 0);
//            value = attributes.getInt(R.styleable.TimePicker_defaultValue, 0);
//            Log.d(TAG, "value: " + value);
//        } finally {
//            attributes.recycle();
//        }
//        hourPicker.setValue(value);


    }


    public int isDefaultValue() {
        Log.d(TAG, "isDefaultValue: ");
        return hourPicker.getValue();
    }

    public void setDefaultValue(int value) {
        Log.d(TAG, "setDefaultValue: " + value);
        hourPicker.setValue(value);
        invalidate();
        requestLayout();
    }


    @BindingAdapter("onTimeChanged")
//        @BindingAdapter("app:onTimeChanged")
    public static void setOnTimeChanged(TimePicker timePicker, OnTimeChangedListener listener) {
        timePicker.setOnTimeChangedListener(listener);
    }



    private void onTimeChanged(){
        if (onTimeChangedListener != null)
            onTimeChangedListener.onTimeChanged(this, hourPicker.getValue(), minutePicker.getValue());
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The hour (0-23).
     */
    public Integer getHour() {
        return hourPicker.getValue();
    }

    /**
     * Set the hour.
     */
    public void setHour(Integer hour) {
        hourPicker.setValue(hour);
        onTimeChanged();
    }

    /**
     * @return The minute.
     */
    public Integer getMinute() {
        return minutePicker.getValue();
    }

    /**
     * Set the minute (0-59).
     */
    public void setMinute(Integer minute) {
        minutePicker.setValue(minute);
        onTimeChanged();
    }


    /**
     * Used to save / restore state of time picker
     */
    private static class SavedState extends BaseSavedState {

        private final int hour;
        private final int minute;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            this.hour = hour;
            this.minute = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            hour = in.readInt();
            minute = in.readInt();
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(hour);
            dest.writeInt(minute);
        }

        public static final Parcelable.Creator<SavedState> CREATOR  = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, getHour(), getMinute());
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setHour(ss.getHour());
        setMinute(ss.getMinute());
    }
}
