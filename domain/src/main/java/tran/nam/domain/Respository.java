package tran.nam.domain;

import org.reactivestreams.Publisher;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.entity.ListRingToneEntity;
import tran.nam.domain.entity.PreferenceEntity;
import tran.nam.domain.mapper.DataEntityMapper;
import tran.nam.domain.mapper.ListRingToneEntityMapper;
import tran.nam.domain.mapper.PreferenceEntityMapper;
import tran.nam.flatform.database.DBProvider;
import tran.nam.flatform.local.IPreference;
import tran.nam.flatform.model.AlarmData;
import tran.nam.flatform.model.RingToneData;

@Singleton
public class Respository implements IRespository {

    private final IPreference iPref;
    private PreferenceEntityMapper mPrefMapper;
    private final DBProvider mDbProvider;
    private final DataEntityMapper mDataMaper;
    private final ListRingToneEntityMapper mListRingToneEntityMapper;

    @Inject
    Respository(IPreference iPreference, PreferenceEntityMapper mPrefMapper, DBProvider mDbProvider, DataEntityMapper mDataMaper
            , ListRingToneEntityMapper mListRingToneEntityMapper) {
        this.iPref = iPreference;
        this.mPrefMapper = mPrefMapper;
        this.mDbProvider = mDbProvider;
        this.mDataMaper = mDataMaper;
        this.mListRingToneEntityMapper = mListRingToneEntityMapper;
    }

    @Override
    public PreferenceEntity getSetting() {
        return mPrefMapper.transform(iPref);
    }

    @Override
    public void updateSetting(boolean is24h, boolean isWetMode, ListRingToneEntity ringtone) {
        iPref.setAlarm24h(is24h);
        iPref.setWetMode(isWetMode);
        iPref.setListDefaultRingTone(mListRingToneEntityMapper.transform(ringtone));
    }

    @Override
    public Flowable<List<AlarmEntity>> fetchAlarm(int type) {
        switch (type) {
            case 1:
                return mDbProvider.alarmDao().fetchAlarm(false).flatMap((Function<List<AlarmData>, Publisher<List<AlarmEntity>>>) dataList
                        -> Flowable.just(mDataMaper.getAlarmEntityDataMapper().transform(dataList)));
            case 2:
                return mDbProvider.alarmDao().fetchAlarm(true).flatMap((Function<List<AlarmData>, Publisher<List<AlarmEntity>>>) dataList
                        -> Flowable.just(mDataMaper.getAlarmEntityDataMapper().transform(dataList)));
            default:
                return mDbProvider.alarmDao().fetchAlarm().flatMap((Function<List<AlarmData>, Publisher<List<AlarmEntity>>>) dataList
                        -> Flowable.just(mDataMaper.getAlarmEntityDataMapper().transform(dataList)));
        }

    }

    @Override
    public Flowable<Long> saveAlarm(AlarmData data) {
        if (data == null)
            return Flowable.error(new IllegalArgumentException("AlarmData cannot be null"));

        return Flowable.fromCallable(() -> {
            long id;
            mDbProvider.beginTransaction();
            try {
                id = mDbProvider.alarmDao().insert(data);
                mDbProvider.setTransactionSuccessful();
            } finally {
                mDbProvider.endTransaction();
            }
            return id;
        }).flatMap((Function<Long, Publisher<Long>>) Flowable::just);
    }

    @Override
    public Completable updateAlarm(AlarmData data) {
        if (data == null)
            return Completable.error(new IllegalArgumentException("AlarmData cannot be null"));
        return Completable.fromAction(() -> {
            mDbProvider.beginTransaction();
            try {
                mDbProvider.alarmDao().update(data);
                mDbProvider.setTransactionSuccessful();
            } finally {
                mDbProvider.endTransaction();
            }
        });
    }

    @Override
    public Completable deleteAlarm(AlarmData data) {
        if (data == null)
            return Completable.error(new IllegalArgumentException("AlarmData cannot be null"));
        return Completable.fromAction(() -> {
            mDbProvider.beginTransaction();
            try {
                mDbProvider.alarmDao().delete(data);
                mDbProvider.setTransactionSuccessful();
            } finally {
                mDbProvider.endTransaction();
            }
        });
    }
}
