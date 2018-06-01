package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.databinding.ObservableField;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.core.model.BaseViewModel;
import tran.nam.domain.IRespository;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.interactor.GetListAlarmUseCase;
import tran.nam.util.Logger;

public class HomeViewModel extends BaseViewModel {

    public ObservableField<String> year, month, day, date, hour, minute, second;

    private Handler timeHandler;
    private Runnable runnable;

    public IRespository iRespository;
    public PreferenceModel mPreferenceModel;
    private final GetListAlarmUseCase mGetListAlarmUseCase;
    private final DataMapper mDataMapper;
    private final AlarmController mAlarmController;
    private onLoadingDialog onLoadingDialog;
    private onUpdateData onUpdateData;

    @Inject
    HomeViewModel(@NonNull Application application, IRespository iRespository, PreferenceModel mPreferenceModel, DataMapper dataMapper, GetListAlarmUseCase mGetListAlarmUseCase, AlarmController mAlarmController) {
        super(application);

        this.iRespository = iRespository;
        this.mPreferenceModel = mPreferenceModel;
        this.mGetListAlarmUseCase = mGetListAlarmUseCase;
        this.mDataMapper = dataMapper;
        this.mAlarmController = mAlarmController;
        mDataMapper.getPreferenceMapper().transform(iRespository.getSetting());

        year = new ObservableField<>();
        month = new ObservableField<>();
        day = new ObservableField<>();
        date = new ObservableField<>();
        hour = new ObservableField<>();
        minute = new ObservableField<>();
        second = new ObservableField<>();
        getDateTime();
        timeHandler = new Handler();
        runnable = () -> {
            getDateTime();
            updateTime();
        };
        updateTime();
    }

    @Override
    public void detach() {
        cancelTime();
        timeHandler = null;
        runnable = null;
    }

    public void updateTime() {
        timeHandler.postDelayed(runnable, 1000);
    }

    public void cancelTime() {
        timeHandler.removeCallbacks(runnable);
    }

    private void getDateTime() {

        //get date & time
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);
        int ss = calendar.get(Calendar.SECOND);
        int mm = calendar.get(Calendar.MINUTE);
        int hh = calendar.get(Calendar.HOUR_OF_DAY);

        this.year.set(year > 9 ? String.valueOf(year) : String.format("%s%s", "0", String.valueOf(year)));
        this.month.set(month > 9 ? String.valueOf(month + 1) : String.format("%s%s", "0", String.valueOf(month + 1)));
        this.day.set(day > 9 ? String.valueOf(day) : String.format("%s%s", "0", String.valueOf(day)));
        this.date.set(getDayOfWeek(day_of_week));
        this.hour.set(hh > 9 ? String.valueOf(hh) : String.format("%s%s", "0", String.valueOf(hh)));
        this.minute.set(mm > 9 ? String.valueOf(mm) : String.format("%s%s", "0", String.valueOf(mm)));
        this.second.set(ss > 9 ? String.valueOf(ss) : String.format("%s%s", "0", String.valueOf(ss)));

    }

    private String getDayOfWeek(int day_of_week) {
        switch (day_of_week) {
            case Calendar.MONDAY:
                return "MON";
            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
            case Calendar.SUNDAY:
                return "SUN";
        }

        return null;

    }

    public void setOnUpdateData(HomeViewModel.onUpdateData onUpdateData) {
        this.onUpdateData = onUpdateData;
    }

    public void setOnLoadingDialog(onLoadingDialog onLoadingDialog) {
        this.onLoadingDialog = onLoadingDialog;
    }

    public void onCheckWetMode(boolean isChecked) {
        mPreferenceModel.isWetMode = isChecked;
        iRespository.updateSetting(mPreferenceModel.is24h, mPreferenceModel.isWetMode, mPreferenceModel.defaultRingtone.name, mPreferenceModel.defaultRingtone.uri);
        mGetListAlarmUseCase.execute(new GetListAlarm(), 0);
    }

    private final class GetListAlarm extends DisposableSubscriber<List<AlarmEntity>> {

        @Override
        protected void onStart() {
            super.onStart();
            if (onLoadingDialog != null)
                onLoadingDialog.onShowLoadingDialog();
        }

        @Override
        public void onNext(List<AlarmEntity> alarmEntities) {
            List<AlarmModel> alarmModels = mDataMapper.getAlarmMapper().transform(alarmEntities);
            for (AlarmModel alarmModel : alarmModels) {
                if (alarmModel.isEnable) {
                    mAlarmController.cancelAlarm(alarmModel);
                }

                if (alarmModel.isWetMode == mPreferenceModel.isWetMode && alarmModel.isEnable) {
                    mAlarmController.scheduleAlarm(alarmModel);
                }
            }

            if (onLoadingDialog != null)
                onLoadingDialog.onHideLoadingDialog();

            if (onUpdateData != null)
                onUpdateData.onUpdateData();
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
            if (onLoadingDialog != null)
                onLoadingDialog.onShowError(t.getMessage());
        }

        @Override
        public void onComplete() {

        }
    }

    public interface onLoadingDialog {
        void onShowLoadingDialog();

        void onHideLoadingDialog();

        void onShowError(String error);
    }

    public interface onUpdateData{
        void onUpdateData();
    }
}
