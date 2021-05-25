package com.schedule.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
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
}
