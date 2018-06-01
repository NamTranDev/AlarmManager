package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.Calendar;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.core.model.BaseViewModel;
import tran.nam.core.state.Resource;
import tran.nam.domain.interactor.SaveAlarmUseCase;
import tran.nam.domain.interactor.UpdateAlarmUseCase;
import tran.nam.util.Logger;

import static tran.nam.util.Constant.EMPTY;

public class AddAlarmViewModel extends BaseViewModel {

    public ToolbarModel toolbarModel;
    public AlarmModel alarmModel;
    private final SaveAlarmUseCase mSaveAlarmUseCase;
    private final UpdateAlarmUseCase mUpdateAlarmUseCase;
    private DataMapper dataMapper;
    private PreferenceModel mPreferenceModel;
    private AlarmController mAlarmController;
    private OnAddOrUpdateAlarmCallBack onAddOrUpdateAlarmCallBack;

    private MutableLiveData<Resource<Boolean>> results = new MutableLiveData<>();

    @Inject
    AddAlarmViewModel(@NonNull Application application, AlarmController alarmController, SaveAlarmUseCase mSaveAlarmUseCase, UpdateAlarmUseCase mUpdateAlarmUseCase, PreferenceModel mPreferenceModel, DataMapper dataMapper) {
        super(application);
        this.mAlarmController = alarmController;
        this.mSaveAlarmUseCase = mSaveAlarmUseCase;
        this.mUpdateAlarmUseCase = mUpdateAlarmUseCase;
        this.dataMapper = dataMapper;
        this.mPreferenceModel = mPreferenceModel;
        dataMapper.getPreferenceMapper().transform(mSaveAlarmUseCase.iRepository.getSetting());
        toolbarModel = new ToolbarModel(true, true
                , false, true, application.getString(R.string.text_cancel), application.getString(R.string.text_done));
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        int currentMinute = rightNow.get(Calendar.MINUTE);
        alarmModel = new AlarmModel("Bell", currentHour, currentMinute, new int[]{2,3,4,5,6}, this.mPreferenceModel.defaultRingtone, 0,3, this.mPreferenceModel.isWetMode, true);
        alarmModel.is24h = this.mPreferenceModel.is24h;
    }

    @Override
    public void detach() {
        mSaveAlarmUseCase.dispose();
        onCleared();
    }

    public void setOnAddOrUpdateAlarmCallBack(OnAddOrUpdateAlarmCallBack onAddOrUpdateAlarmCallBack) {
        this.onAddOrUpdateAlarmCallBack = onAddOrUpdateAlarmCallBack;
    }

    public AlarmModel setAlarm(AlarmModel alarm) {
        this.alarmModel = alarm;
        alarmModel.is24h = this.mPreferenceModel.is24h;
        return alarmModel;
    }

    public void saveAlarm() {
        mSaveAlarmUseCase.execute(new SaveAlarmResponse(), dataMapper.getAlarmMapper().convert(alarmModel));
    }

    public void updateAlarm(){
        mAlarmController.cancelAlarm(alarmModel);
        alarmModel.isEnable = true;
        mUpdateAlarmUseCase.execute(new UpdateAlarmResponse(),dataMapper.getAlarmMapper().convert(alarmModel));
    }

    private final class SaveAlarmResponse extends DisposableSubscriber<Long> {

        @Override
        public void onNext(Long aLong) {
            alarmModel.id = aLong;
            mAlarmController.scheduleAlarm(alarmModel);
            if (onAddOrUpdateAlarmCallBack != null)
                onAddOrUpdateAlarmCallBack.onFinish();
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
        }

        @Override
        public void onComplete() {

        }
    }

    private final class UpdateAlarmResponse extends DisposableCompletableObserver {

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
        }

        @Override
        public void onComplete() {
            if (alarmModel != null) {
                if (alarmModel.isEnable) {
                    mAlarmController.scheduleAlarm(alarmModel);
                }
            }
            if (onAddOrUpdateAlarmCallBack != null)
                onAddOrUpdateAlarmCallBack.onFinish();
        }
    }

    public interface OnAddOrUpdateAlarmCallBack{
        void onFinish();
    }
}
