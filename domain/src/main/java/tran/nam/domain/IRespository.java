package tran.nam.domain;

import android.arch.lifecycle.LiveData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.entity.PreferenceEntity;
import tran.nam.flatform.model.AlarmData;

public interface IRespository {

    PreferenceEntity getSetting();

    void updateSetting(boolean is24h, boolean isWetMode, String name, String uri);

    Flowable<List<AlarmEntity>> fetchAlarm(int isWetMode);

    Flowable<Long> saveAlarm(AlarmData data);

    Completable updateAlarm(AlarmData data);

    Completable deleteAlarm(AlarmData data);
}
