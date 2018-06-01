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
import android.widget.Toast;

import java.util.Objects;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.callback.DialogItemClick;
import tran.nam.alarmtimer.databinding.DialogNameAlarmBinding;
import tran.nam.util.Constant;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class NameAlarmDialog extends DialogFragment implements DialogItemClick {

    private DialogNameAlarmBinding biding;
    private OnNameAlarmCallback onNameAlarmCallback;

    public static NameAlarmDialog getInstanxe(String name) {
        NameAlarmDialog dialog = new NameAlarmDialog();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_DIALOG.DIALOG_NAME,name);
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
        biding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_name_alarm, container, false);
        if (getArguments() != null){
            String name = getArguments().getString(Constant.KEY_DIALOG.DIALOG_NAME);
            biding.edtName.setText(name);
        }
        biding.setItemClick(this);
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

    public void setOnNameAlarmCallback(OnNameAlarmCallback onNameAlarmCallback) {
        this.onNameAlarmCallback = onNameAlarmCallback;
    }

    protected float maxHeight() {
        return 0.35f;
    }

    protected float maxWidth() {
        return 0.6f;
    }

    @Override
    public void onOkClick() {
        String name = biding.edtName.getText().toString();
        if (TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(),"Please Input lable",Toast.LENGTH_SHORT).show();
            return;
        }

        if (onNameAlarmCallback != null){
            onNameAlarmCallback.onNameAlarmCallback(name);
            dismiss();
        }

    }

    @Override
    public void onCancelClick() {
        dismiss();
    }

    public interface OnNameAlarmCallback{
        void onNameAlarmCallback(String name);
    }
}
