package tran.nam.alarmtimer.application.model;

import android.databinding.BaseObservable;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.domain.entity.PreferenceEntity;

@Singleton
public class PreferenceModel extends BaseObservable{
   public boolean is24h;
   public boolean isWetMode;
   public RingToneModel defaultRingtone;

    @Inject
    PreferenceModel() {
    }
}
