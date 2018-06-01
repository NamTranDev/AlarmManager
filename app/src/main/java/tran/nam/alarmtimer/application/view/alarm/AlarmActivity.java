package tran.nam.alarmtimer.application.view.alarm;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.viewmodel.AlarmActivityViewModel;
import tran.nam.alarmtimer.callback.DialogItemClick;
import tran.nam.alarmtimer.databinding.ActivityAlarmBinding;
import tran.nam.core.view.mvvm.BaseActivityMVVM;
import tran.nam.util.Constant;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class AlarmActivity extends BaseActivityMVVM<ActivityAlarmBinding, AlarmActivityViewModel> implements DialogItemClick, AlarmActivityViewModel.OnAlarmCallback {

    @Inject
    NavigatorApp mNavigatorApp;

    private boolean isPreview;

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AlarmActivityViewModel.class);
        return R.layout.activity_alarm;
    }

    @Override
    protected void setStatusBar() {}

    @Override
    public void initView(Bundle savedInstanceState) {

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        mViewDataBinding.setItemClick(this);
        mViewModel.setOnAlarmCallback(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent() != null && getIntent().getExtras() != null) {
            String data = getIntent().getStringExtra(Constant.KEY_INTENT.ALARM_DATA);
            isPreview = getIntent().getBooleanExtra(Constant.KEY_INTENT.IS_PREVIEW_ALARM,false);
            if (!TextUtils.isEmpty(data)){
                AlarmModel model = new Gson().fromJson(data,AlarmModel.class);
                if (model != null)
                    mViewDataBinding.setAlarm(mViewModel.setAlarm(model,!isPreview));
            }

        }
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getWindow();
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
        }
        super.onResume();
    }

    protected float maxHeight() {
        return 0;
    }

    protected float maxWidth() {
        return 0.5f;
    }

    @Override
    public void onOkClick() {
        onFinishCountDown();
    }

    @Override
    public void onCancelClick() {

    }

    @Override
    public void onBackPressed() {}

    @Override
    public void onFinishCountDown() {
        if (!isPreview)
            mViewModel.checkScheduleAlarm();
        else
            onFinish();
    }

    @Override
    public void onFinish() {
            mNavigatorApp.finish(this);
    }
}
