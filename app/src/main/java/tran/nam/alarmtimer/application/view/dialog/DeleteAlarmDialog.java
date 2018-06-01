package tran.nam.alarmtimer.application.view.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.gson.Gson;

import java.util.Objects;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.databinding.DialogDeleteAlarmBinding;
import tran.nam.util.Constant;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class DeleteAlarmDialog extends DialogFragment {

    private OnDeleteAlarmCallback onDeleteAlarmCallback;
    private AlarmModel alarmModel;

    public static DeleteAlarmDialog getInstance(AlarmModel alarmModel) {
        DeleteAlarmDialog dialog = new DeleteAlarmDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_DIALOG.DIALOG_ALARM_DATA, new Gson().toJson(alarmModel));
        dialog.setArguments(bundle);
        return dialog;
    }

    public void setOnDeleteAlarmCallback(OnDeleteAlarmCallback onDeleteAlarmCallback) {
        this.onDeleteAlarmCallback = onDeleteAlarmCallback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
//        if (dialog.getWindow() != null) {
//            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
//        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogDeleteAlarmBinding biding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_delete_alarm, container, false);

        if (getArguments() != null) {
            String data = getArguments().getString(Constant.KEY_DIALOG.DIALOG_ALARM_DATA);
            if (!TextUtils.isEmpty(data))
                alarmModel = new Gson().fromJson(data, AlarmModel.class);
        }
        biding.btCancel.setOnClickListener(v -> {
            dismiss();
        });
        biding.btDelete.setOnClickListener(v -> {
            dismiss();
            if (onDeleteAlarmCallback != null && alarmModel != null)
                onDeleteAlarmCallback.onDeleteItem(alarmModel);
        });
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

    protected float maxHeight() {
        return 0;
    }

    protected float maxWidth() {
        return 0.6f;
    }

    public interface OnDeleteAlarmCallback {
        void onDeleteItem(AlarmModel alarmModel);
    }
}
