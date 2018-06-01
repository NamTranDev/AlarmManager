package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.core.model.BaseViewModel;
import tran.nam.domain.IRespository;

public class RingtoneViewModel extends BaseViewModel {

    public ToolbarModel toolbarModel;
    private PreferenceModel mPref;
    private IRespository iRespository;

    @Inject
    RingtoneViewModel(@NonNull Application application, IRespository iRespository, PreferenceModel mPref) {
        super(application);
        this.iRespository = iRespository;
        this.mPref = mPref;
        toolbarModel = new ToolbarModel(true, application.getString(R.string.title_ring_tone), R.drawable.ic_back);
        toolbarModel.isIvOptionalEnd = false;
        toolbarModel.srcOptionalEnd = R.drawable.ic_add;
    }

    public void updateSetting(String name, String uri) {
        mPref.defaultRingtone.name = name;
        mPref.defaultRingtone.uri = uri;
        iRespository.updateSetting(mPref.is24h, mPref.isWetMode, mPref.defaultRingtone.name, mPref.defaultRingtone.uri);
    }

    public void fromSetting() {
        toolbarModel.hideIvEnd();
    }

    @Override
    public void detach() {
    }


}
