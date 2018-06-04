package tran.nam.alarmtimer.application.model;

import android.databinding.BaseObservable;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.mapper.ListRingToneMapper;
import tran.nam.domain.entity.ListRingToneEntity;
import tran.nam.domain.entity.PreferenceEntity;

@Singleton
public class PreferenceModel extends BaseObservable{
   public boolean is24h;
   public boolean isWetMode;
   public ListRingToneModel defaultRingtone;

   private ListRingToneMapper listRingToneMapper;

    @Inject
    PreferenceModel(ListRingToneMapper listRingToneMapper) {
        this.listRingToneMapper = listRingToneMapper;
    }

    public ListRingToneEntity listRingToneEntity(){
        return listRingToneMapper.convert(defaultRingtone);
    }
}
