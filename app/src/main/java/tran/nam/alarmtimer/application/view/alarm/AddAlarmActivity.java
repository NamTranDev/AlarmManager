package tran.nam.alarmtimer.application.view.alarm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.NavigatorApp;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.view.dialog.DurationAlarmDialog;
import tran.nam.alarmtimer.application.view.dialog.NameAlarmDialog;
import tran.nam.alarmtimer.application.viewmodel.AddAlarmViewModel;
import tran.nam.alarmtimer.callback.AddAlarmItemClick;
import tran.nam.alarmtimer.callback.ToolbarItemClick;
import tran.nam.alarmtimer.databinding.ActivityAddAlarmBinding;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.alarmtimer.widget.OnWeekdaysChangeListener;
import tran.nam.alarmtimer.widget.TimePicker;
import tran.nam.core.view.mvvm.BaseActivityMVVM;
import tran.nam.util.Constant;
import tran.nam.util.Logger;
import tran.nam.util.StatusBarUtil;

import static tran.nam.alarmtimer.type.RingToneType.MUSIC;
import static tran.nam.alarmtimer.type.RingToneType.TONE;

@SuppressWarnings("unchecked")
public class AddAlarmActivity extends BaseActivityMVVM<ActivityAddAlarmBinding, AddAlarmViewModel> implements ToolbarItemClick.OnTvOptionalStartClick
        , ToolbarItemClick.OnTvOptionalEndClick, AddAlarmItemClick, NameAlarmDialog.OnNameAlarmCallback, DurationAlarmDialog.OnDurationAlarmCallback, TimePicker.OnTimeChangedListener, OnWeekdaysChangeListener, AddAlarmViewModel.OnAddOrUpdateAlarmCallBack {

    @Inject
    NavigatorApp mNavigatorApp;

    private boolean isEdit;

    @Override
    public int getLayoutId() {
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddAlarmViewModel.class);
        return R.layout.activity_add_alarm;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, ContextCompat.getColor(this, R.color.toolbar_color));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mViewDataBinding.setToolbarModel(mViewModel.toolbarModel);
        mViewDataBinding.setTvOptionalStartClick(this);
        mViewDataBinding.setTvOptionalEndClick(this);
        mViewDataBinding.setItemClick(this);
        mViewDataBinding.datePicker.setOnWeekdaysChangeListener(this);
        mViewDataBinding.timePicker.setOnTimeChangedListener(this);
        mViewModel.setOnAddOrUpdateAlarmCallBack(this);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        if (getIntent().getExtras() != null) {
            String data = getIntent().getExtras().getString(Constant.KEY_INTENT.ALARM_DATA);
            if (!TextUtils.isEmpty(data)) {
                AlarmModel alarmModel = new Gson().fromJson(data, AlarmModel.class);
                if (alarmModel != null) {
                    mViewDataBinding.setAlarmModel(mViewModel.setAlarm(alarmModel));
                    isEdit = true;
                }
            }
        }
        if (!isEdit)
            mViewDataBinding.setAlarmModel(mViewModel.alarmModel);
        mViewDataBinding.datePicker.setSelectedDays(mViewModel.alarmModel.getListDate());
        mViewDataBinding.timePicker.setCurrentHour(mViewModel.alarmModel.hour);
        mViewDataBinding.timePicker.setCurrentMinute(mViewModel.alarmModel.minute);
    }

    @Override
    public void onTvOptionalStartClick(int type) {
        onBackPressed();
    }

    @Override
    public void onTvOptionalEndClick(int type) {
        if (isEdit) {
            mViewModel.updateAlarm();
        } else {
            mViewModel.saveAlarm();
        }
    }

    @Override
    public void onBackPressed() {
        mNavigatorApp.finish(this);
    }

    @Override
    public void onLableItemClick() {
        NameAlarmDialog dialog = NameAlarmDialog.getInstanxe(mViewModel.alarmModel.lable);
        dialog.setOnNameAlarmCallback(this);
        dialog.show(getSupportFragmentManager(), "Lable Dialog");
    }

    @Override
    public void onSongItemClick() {
        mNavigatorApp.goToRingTonePick(this, false, TONE, mViewModel.alarmModel.ringtone);
    }

    @Override
    public void onSongMusicItemClick() {
        mNavigatorApp.goToRingTonePick(this, false, MUSIC, mViewModel.alarmModel.ringtoneMusic);
    }

    @Override
    public void onDurationItemClick() {
        DurationAlarmDialog dialog = DurationAlarmDialog.getInstanxe(TONE, (int) mViewModel.alarmModel.durationMinute, (int) mViewModel.alarmModel.durationSecond);
        dialog.setOnDurationAlarmCallback(this);
        dialog.show(getSupportFragmentManager(), "Duration Dialog");
    }

    @Override
    public void onDurationMusicItemClick() {
        DurationAlarmDialog dialog = DurationAlarmDialog.getInstanxe(MUSIC, (int) mViewModel.alarmModel.durationMusicMinute, (int) mViewModel.alarmModel.durationMusicSecond);
        dialog.setOnDurationAlarmCallback(this);
        dialog.show(getSupportFragmentManager(), "Duration Music Dialog");
    }

    @Override
    public void onPreviewItemClick() {
        mNavigatorApp.goToAlarm(this, true, mViewModel.alarmModel);
    }

    @Override
    public void onNameAlarmCallback(String name) {
        mViewModel.alarmModel.lable = name;
        mViewModel.alarmModel.notifyChange();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CODE.PICK_RING_TONE:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mViewModel.alarmModel.ringtone = data.getParcelableExtra(Constant.KEY_INTENT_RESULT.RING_TONE);
                        mViewModel.alarmModel.notifyChange();
                    }
                }
                break;
            case Constant.REQUEST_CODE.PICK_RING_MUSIC:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        mViewModel.alarmModel.ringtoneMusic = data.getParcelableExtra(Constant.KEY_INTENT_RESULT.RING_TONE);
                        if (TextUtils.isEmpty(mViewModel.alarmModel.ringtoneMusic.ringToneModels.get(0).uri)){
                            mViewModel.alarmModel.durationMusicMinute = 0;
                            mViewModel.alarmModel.durationMusicSecond = 0;
                        }else {
                            mViewModel.alarmModel.durationMusicMinute = 0;
                            mViewModel.alarmModel.durationMusicSecond = 3;
                        }
                        mViewModel.alarmModel.notifyChange();
                    }
                }
                break;
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        mViewModel.alarmModel.hour = hourOfDay;
        mViewModel.alarmModel.minute = minute;
        Logger.debug("Time Picker", hourOfDay + " : " + minute);
    }

    @Override
    public void onChange(View view, int clickedDayOfWeek, List<Integer> selectedDays) {
        int[] date = new int[selectedDays.size()];

        for (int i = 0; i < selectedDays.size(); i++) {
            if (selectedDays.get(i) != null) {
                date[i] = selectedDays.get(i);
            }
        }
        mViewModel.alarmModel.day = date;
    }

    @Override
    public void onFinish() {
        onBackPressed();
    }

    @Override
    public void onDurationAlarmCallback(@RingToneType int type, int durationMinute, int durationSecond) {
        switch (type) {
            case RingToneType.MUSIC:
                mViewModel.alarmModel.durationMusicMinute = durationMinute;
                mViewModel.alarmModel.durationMusicSecond = durationSecond;
                break;
            case RingToneType.TONE:
                mViewModel.alarmModel.durationMinute = durationMinute;
                mViewModel.alarmModel.durationSecond = durationSecond;
                break;
        }
        mViewModel.alarmModel.notifyChange();
    }
}
