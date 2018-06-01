package tran.nam.alarmtimer.application.view.dialog;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import tran.nam.alarmtimer.databinding.DialogAlarmBinding;
import tran.nam.alarmtimer.type.AlarmType;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static tran.nam.alarmtimer.type.AlarmType.ALERT;
import static tran.nam.alarmtimer.type.AlarmType.BELL;
import static tran.nam.alarmtimer.type.AlarmType.EMPTY;
import static tran.nam.alarmtimer.type.AlarmType.EVAC;

public class AlarmDialog extends DialogFragment implements DialogItemClick, MediaPlayer.OnCompletionListener {

    private MediaPlayer mPlayer;
    private @AlarmType String type = EMPTY;

    public static AlarmDialog getInstance(String type){
        AlarmDialog dialog = new AlarmDialog();
        Bundle bundle = new Bundle();
        bundle.putString("Type AlarmModel",type);
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null){
            type = getArguments().getString("Type AlarmModel");
            assert type != null;
            switch (type){
                case ALERT:
                    mPlayer = MediaPlayer.create(getContext(), R.raw.alert);
                    break;
                case EVAC:
                    mPlayer = MediaPlayer.create(getContext(), R.raw.evac);
                    break;
                case BELL:
                    mPlayer = MediaPlayer.create(getContext(), R.raw.bell);
                    break;
            }
            if (mPlayer == null)
                return;
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mPlayer.setLooping(true);
            mPlayer.setOnCompletionListener(this);
            mPlayer.start();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
//        if (dialog.getWindow() != null){
//            dialog.getWindow().getAttributes().windowAnimations = R.style.Dialog;
//        }
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogAlarmBinding biding = DataBindingUtil.inflate(inflater,
                R.layout.dialog_alarm, container, false);
        biding.tvTitle.setText(type);
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

    protected float maxHeight() {
        return 0;
    }

    protected float maxWidth() {
        return 0.4f;
    }

    @Override
    public void onOkClick() {
        if (mPlayer == null){
            dismiss();
            return;
        }

        mPlayer.stop();
        mPlayer = null;
        dismiss();
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayer == null)
            return;
        mPlayer.start();
    }
}
