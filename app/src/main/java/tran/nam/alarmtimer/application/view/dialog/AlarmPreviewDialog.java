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
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.callback.DialogItemClick;
import tran.nam.alarmtimer.databinding.DialogAlarmPreviewBinding;
import tran.nam.util.Constant;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AlarmPreviewDialog extends DialogFragment implements DialogItemClick{

    public static AlarmPreviewDialog getInstance(AlarmModel alarmModel){
        AlarmPreviewDialog dialog = new AlarmPreviewDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.KEY_DIALOG.ALARM,alarmModel);
        dialog.setArguments(bundle);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
//        if (dialog.getWindow() != null){
//            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
//        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogAlarmPreviewBinding binding = DataBindingUtil.inflate(inflater,R.layout.dialog_alarm_preview,container,false);
        if (getArguments() != null){
            AlarmModel alarmModel = getArguments().getParcelable(Constant.KEY_DIALOG.ALARM);
            if (alarmModel != null)
                binding.setAlarm(alarmModel);
        }
        binding.setItemClick(this);
        return binding.getRoot();
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

    protected float maxHeight() {
        return 0;
    }

    protected float maxWidth() {
        return 0.4f;
    }

    @Override
    public void onOkClick() {
        dismiss();
    }

    @Override
    public void onCancelClick() {

    }
}
