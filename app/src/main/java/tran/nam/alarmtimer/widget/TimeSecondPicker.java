package tran.nam.alarmtimer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import java.util.Locale;
import java.util.Objects;

import tran.nam.alarmtimer.R;

public class TimeSecondPicker extends FrameLayout {

    private int mCurrentMinute = 0; // 0-59
    private int mCurrentSeconds = 0; // 0-59

    private final NumberPicker mMinutePicker;
    private final NumberPicker mSecondPicker;

    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = (view, minute, seconds) -> {
    };

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view    The view associated with this listener.
         * @param minute  The current minute.
         * @param seconds The current second.
         */
        void onTimeChanged(TimeSecondPicker view, int minute, int seconds);
    }

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            value -> {
                // TODO Auto-generated method stub
                return String.format(Locale.getDefault(), "%02d", value);
            };

    public TimeSecondPicker(Context context) {
        this(context, null);
    }

    public TimeSecondPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimeSecondPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        Objects.requireNonNull(inflater).inflate(R.layout.time_picker_widget, this, true);

        // digits of minute
        mMinutePicker = findViewById(R.id.minute);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(9);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        mMinutePicker.setOnValueChangedListener((spinner, oldVal, newVal) -> {
            mCurrentMinute = newVal;
            onTimeChanged();
        });

        // digits of seconds
        mSecondPicker = findViewById(R.id.seconds);
        mSecondPicker.setMinValue(1);
        mSecondPicker.setMaxValue(59);
        mSecondPicker.setFormatter(TWO_DIGIT_FORMATTER);
        mSecondPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mCurrentSeconds = newVal;
            onTimeChanged();
        });

        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        // set to current time
        setCurrentMinute(0);
        setCurrentSecond(3);

        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    /**
     * Set the callback that indicates the time has been adjusted by the user.
     *
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    /**
     * Set the current minute (0-59).
     */
    public void setCurrentMinute(Integer currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }

    /**
     * @return The current minute.
     */
    public Integer getCurrentSeconds() {
        return mCurrentSeconds;
    }

    /**
     * Set the current second (0-59).
     */
    public void setCurrentSecond(Integer currentSecond) {
        this.mCurrentSeconds = currentSecond;
        updateSecondsDisplay();
    }

    private void onTimeChanged() {
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }

    /**
     * Set the state of the spinners appropriate to the current second.
     */
    private void updateSecondsDisplay() {
        mSecondPicker.setValue(mCurrentSeconds);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds());
    }

    public void setMaxMinute(int minute){
        mMinutePicker.setMaxValue(minute);
    }
}
