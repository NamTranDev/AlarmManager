package tran.nam.alarmtimer.application.view.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.Objects;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.callback.DialogItemClick;
import tran.nam.alarmtimer.databinding.DialogMinuteAlarmBinding;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.util.Constant;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DurationAlarmDialog extends DialogFragment implements DialogItemClick {

    private DialogMinuteAlarmBinding biding;
    private OnDurationAlarmCallback OnDurationAlarmCallback;
    private @RingToneType int type;

    public static DurationAlarmDialog getInstanxe(@RingToneType int type, int durationMinute, int durationSecond) {
        DurationAlarmDialog dialog = new DurationAlarmDialog();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_DIALOG.DIALOG_MINUTE,durationMinute);
        bundle.putInt(Constant.KEY_DIALOG.DIALOG_SECOND,durationSecond);
        bundle.putInt(Constant.KEY_DIALOG.DIALOG_TYPE,type);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        biding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_minute_alarm, container, false);
        biding.setItemClick(this);
        if (getArguments() != null){
            int durationMinute = getArguments().getInt(Constant.KEY_DIALOG.DIALOG_MINUTE);
            int durationSecond = getArguments().getInt(Constant.KEY_DIALOG.DIALOG_SECOND);
            type = getArguments().getInt(Constant.KEY_DIALOG.DIALOG_TYPE);
            biding.durationPicker.setCurrentSecond(durationSecond);
            biding.durationPicker.setCurrentMinute(durationMinute);
            switch (type) {
                case RingToneType.MUSIC:
                    biding.durationPicker.setMaxMinute(59);
                    break;
                case RingToneType.TONE:
                    biding.durationPicker.setMaxMinute(9);
                    break;
            }
        }
        return biding.getRoot();
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        if (window != null) {
            Point size = new Point();
            // Store dimensions of the screen in `size`
            Display display = window.getWindowManager().getDefaultDisplay();
            display.getSize(size);
            // Set the width of the dialog proportional to 75% of the screen width
            if (maxWidth() != 0 && maxHeight() != 0) {
                window.setLayout((int) (size.x * maxWidth()), (int) (size.y * maxHeight()));
            } else if (maxWidth() != 0) {
                window.setLayout((int) (size.x * maxWidth()), WRAP_CONTENT);
            } else if (maxHeight() != 0) {
                window.setLayout(WRAP_CONTENT, (int) (size.y * maxHeight()));
            } else {
                window.setLayout(WRAP_CONTENT, WRAP_CONTENT);
            }
            window.setGravity(Gravity.CENTER);
            // Call super onResume after sizing

        }
        super.onResume();
    }

    public void setOnDurationAlarmCallback(OnDurationAlarmCallback OnDurationAlarmCallback) {
        this.OnDurationAlarmCallback = OnDurationAlarmCallback;
    }

    protected float maxHeight() {
        return 0.35f;
    }

    protected float maxWidth() {
        return 0.6f;
    }

    @Override
    public void onOkClick() {
        if (OnDurationAlarmCallback != null)
            OnDurationAlarmCallback.onDurationAlarmCallback(type,biding.durationPicker.getCurrentMinute(),biding.durationPicker.getCurrentSeconds());
        dismiss();
    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    public interface OnDurationAlarmCallback {
        void onDurationAlarmCallback(@RingToneType int type,int durationMinute, int durationSecond);
    }
}
