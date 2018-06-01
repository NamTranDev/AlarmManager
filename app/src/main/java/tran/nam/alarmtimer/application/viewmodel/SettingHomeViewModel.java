package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;
import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.core.model.BaseViewModel;
import tran.nam.domain.IRespository;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.interactor.GetListAlarmUseCase;
import tran.nam.util.Logger;

public class SettingHomeViewModel extends BaseViewModel {

    public IRespository iRespository;
    public ToolbarModel toolbarModel;
    public PreferenceModel mPreferenceModel;
    private final GetListAlarmUseCase mGetListAlarmUseCase;
    private final DataMapper mDataMapper;
    private final AlarmController mAlarmController;
    public boolean isChange;
    private onLoadingDialog onLoadingDialog;

    @Inject
    SettingHomeViewModel(@NonNull Application application, IRespository iRespository, PreferenceModel mPreferenceModel, DataMapper dataMapper, GetListAlarmUseCase mGetListAlarmUseCase, AlarmController mAlarmController) {
        super(application);
        this.iRespository = iRespository;
        this.mPreferenceModel = mPreferenceModel;
        this.mGetListAlarmUseCase = mGetListAlarmUseCase;
        this.mDataMapper = dataMapper;
        this.mAlarmController = mAlarmController;
        mDataMapper.getPreferenceMapper().transform(iRespository.getSetting());
        toolbarModel = new ToolbarModel(true, application.getString(R.string.title_setting_home), R.drawable.ic_back);
    }

    @Override
    public void detach() {
        mGetListAlarmUseCase.dispose();
        onCleared();
    }

    public void setOnLoadingDialog(SettingHomeViewModel.onLoadingDialog onLoadingDialog) {
        this.onLoadingDialog = onLoadingDialog;
    }

    public void onCheckChange24H(boolean isChecked){
        mPreferenceModel.is24h = isChecked;
        iRespository.updateSetting(mPreferenceModel.is24h, mPreferenceModel.isWetMode, mPreferenceModel.defaultRingtone.name, mPreferenceModel.defaultRingtone.uri);
        isChange = true;
    }

    public void onCheckWetMode(boolean isChecked){
        mPreferenceModel.isWetMode = isChecked;
        iRespository.updateSetting(mPreferenceModel.is24h, mPreferenceModel.isWetMode, mPreferenceModel.defaultRingtone.name, mPreferenceModel.defaultRingtone.uri);
        mGetListAlarmUseCase.execute(new GetListAlarm(),0);
        isChange = true;
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
            for (AlarmModel alarmModel : alarmModels){
                if (alarmModel.isEnable){
                    mAlarmController.cancelAlarm(alarmModel);
                }

                if (alarmModel.isWetMode == mPreferenceModel.isWetMode && alarmModel.isEnable){
                    mAlarmController.scheduleAlarm(alarmModel);
                }
            }

            if (onLoadingDialog != null)
                onLoadingDialog.onHideLoadingDialog();
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
}
