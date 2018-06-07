package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.alarmtimer.mapper.ListRingToneMapper;
import tran.nam.alarmtimer.type.RingToneType;
import tran.nam.core.model.BaseViewModel;
import tran.nam.domain.IRespository;

public class RingtoneViewModel extends BaseViewModel {

    public ToolbarModel toolbarModel;
    private final PreferenceModel mPref;
    private final IRespository iRespository;

    @Inject
    RingtoneViewModel(@NonNull Application application, IRespository iRespository, PreferenceModel mPref) {
        super(application);
        this.iRespository = iRespository;
        this.mPref = mPref;
        toolbarModel = new ToolbarModel(true, application.getString(R.string.title_ring_tone), R.drawable.ic_back);
        toolbarModel.isTextOptionEnd = true;
        toolbarModel.textOptionEnd = application.getString(R.string.text_done);
    }

    public void fromSetting() {
        toolbarModel.hideIvEnd();
    }

    public void updateSetting(ListRingToneModel listRingToneModel,@RingToneType int typeRingTone) {
        switch (typeRingTone) {
            case RingToneType.MUSIC:
                mPref.defaultRingtoneMusic = listRingToneModel;
                break;
            case RingToneType.TONE:
                mPref.defaultRingtone = listRingToneModel;
                break;
        }

        iRespository.updateSetting(mPref.is24h, mPref.isWetMode, mPref.listRingToneEntity(),mPref.listRingToneMusicEntity());
    }

    @Override
    public void detach() {
    }


}
