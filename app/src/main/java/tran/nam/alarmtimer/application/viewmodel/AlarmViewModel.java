package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.subscribers.DisposableSubscriber;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.core.model.BaseViewModel;
import tran.nam.core.state.Resource;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.interactor.DeleteAlarmUseCase;
import tran.nam.domain.interactor.GetListAlarmUseCase;
import tran.nam.domain.interactor.UpdateAlarmUseCase;
import tran.nam.util.Logger;

public class AlarmViewModel extends BaseViewModel {

    private MutableLiveData<Resource<List<AlarmModel>>> results = new MutableLiveData<>();
    ;
    private PreferenceModel mPreferenceModel;
    private final GetListAlarmUseCase mGetListAlarmUseCase;
    private final UpdateAlarmUseCase mUpdateAlarmUseCase;
    private final DeleteAlarmUseCase mDeleteAlarmUseCase;
    private final DataMapper dataMapper;
    private final AlarmController mAlarmController;
    private AlarmModel alarmModel;
    private onLoadingDialog onLoadingDialog;

    @Inject
    AlarmViewModel(@NonNull Application application, GetListAlarmUseCase getListAlarmUseCase, PreferenceModel mPreferenceModel, UpdateAlarmUseCase mUpdateAlarmUseCase, DeleteAlarmUseCase mDeleteAlarmUseCase, DataMapper dataMapper, AlarmController mAlarmController) {
        super(application);
        this.mPreferenceModel = mPreferenceModel;
        this.mUpdateAlarmUseCase = mUpdateAlarmUseCase;
        this.mDeleteAlarmUseCase = mDeleteAlarmUseCase;
        this.mAlarmController = mAlarmController;
        dataMapper.getPreferenceMapper().transform(getListAlarmUseCase.iRepository.getSetting());
        this.mGetListAlarmUseCase = getListAlarmUseCase;
        this.dataMapper = dataMapper;
    }

    @Override
    public void detach() {
        mGetListAlarmUseCase.dispose();
        onCleared();
    }

    public void setOnLoadingDialog(AlarmViewModel.onLoadingDialog onLoadingDialog) {
        this.onLoadingDialog = onLoadingDialog;
    }

    public MutableLiveData<Resource<List<AlarmModel>>> getAlarms() {
        mGetListAlarmUseCase.execute(new GetListAlarm(), mPreferenceModel.isWetMode ? 2 : 1);
        return results;
    }

    public void updateAlarm(AlarmModel alarmModel) {
        this.alarmModel = alarmModel;
        this.alarmModel.isEnable = !this.alarmModel.isEnable;
        mUpdateAlarmUseCase.execute(new UpdateAlarmResponse(), dataMapper.getAlarmMapper().convert(this.alarmModel));
    }

    public void deleteAlarm(AlarmModel alarmModel) {
        this.alarmModel = alarmModel;
        mDeleteAlarmUseCase.execute(new DeleteAlarmResponse(), dataMapper.getAlarmMapper().convert(this.alarmModel));
    }

    private final class GetListAlarm extends DisposableSubscriber<List<AlarmEntity>> {

        @Override
        protected void onStart() {
            super.onStart();
            results.setValue(Resource.normalLoading(null));
        }

        @Override
        public void onNext(List<AlarmEntity> alarmEntities) {
            List<AlarmModel> alarmModels = dataMapper.getAlarmMapper().transform(alarmEntities);
            for (AlarmModel alarmModel : alarmModels){
                alarmModel.is24h = mPreferenceModel.is24h;
            }
            results.setValue(Resource.success(alarmModels));
        }

        @Override
        public void onError(Throwable t) {
            results.setValue(Resource.error(t.getMessage(), null));
        }

        @Override
        public void onComplete() {

        }
    }

    private final class UpdateAlarmResponse extends DisposableCompletableObserver {

        @Override
        protected void onStart() {
            super.onStart();
            if (onLoadingDialog != null)
                onLoadingDialog.onShowLoadingDialog();
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
            if (onLoadingDialog != null)
                onLoadingDialog.onShowError(t.getMessage());
        }

        @Override
        public void onComplete() {
            if (alarmModel != null) {
                if (alarmModel.isEnable) {
                    mAlarmController.scheduleAlarm(alarmModel);
                } else {
                    mAlarmController.cancelAlarm(alarmModel);
                }
            }
            if (onLoadingDialog != null)
                onLoadingDialog.onHideLoadingDialog();
        }
    }

    private final class DeleteAlarmResponse extends DisposableCompletableObserver {

        @Override
        protected void onStart() {
            super.onStart();
            if (onLoadingDialog != null)
                onLoadingDialog.onShowLoadingDialog();
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
            if (onLoadingDialog != null)
                onLoadingDialog.onShowError(t.getMessage());
        }

        @Override
        public void onComplete() {
            if (alarmModel != null) {
                mAlarmController.cancelAlarm(alarmModel);
            }
            if (onLoadingDialog != null)
                onLoadingDialog.onHideLoadingDialog();
        }
    }

    public interface onLoadingDialog {
        void onShowLoadingDialog();

        void onHideLoadingDialog();

        void onShowError(String error);
    }
}
