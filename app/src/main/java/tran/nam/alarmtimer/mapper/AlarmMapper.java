package tran.nam.alarmtimer.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.util.Checker;

@Singleton
public class AlarmMapper {

    private ListRingToneMapper ringtoneMapper;

    @Inject
    public AlarmMapper(ListRingToneMapper ringtoneMapper) {
        this.ringtoneMapper = ringtoneMapper;
    }

    /**
     * Transform a {@link AlarmEntity} into an {@link AlarmModel}.
     *
     * @param data Object to be transformed.
     * @return {@link AlarmEntity}.
     */
    public AlarmModel transform(AlarmEntity data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final AlarmModel alarmModel = new AlarmModel();
        alarmModel.id = data.id;
        alarmModel.lable = data.lable;
        alarmModel.hour = data.hour;
        alarmModel.minute = data.minute;
        alarmModel.day = data.day;
        alarmModel.ringtone = ringtoneMapper.transform(data.listRingToneEntity);
        alarmModel.ringtoneMusic = ringtoneMapper.transform(data.listRingToneMusicEntity);
        alarmModel.durationMinute = data.durationMinute;
        alarmModel.durationSecond = data.durationSecond;
        alarmModel.durationMusicMinute = data.durationMusicMinute;
        alarmModel.durationMusicSecond = data.durationMusicSecond;
        alarmModel.isEnable = data.isEnable;
        alarmModel.isWetMode = data.isWetMode;

        return alarmModel;
    }

    /**
     * Transform a Collection of {@link AlarmEntity} into a Collection of {@link AlarmModel}.
     *
     * @param dataList Objects to be transformed.
     * @return List of {@link AlarmModel}.
     */
    public List<AlarmModel> transform(List<AlarmEntity> dataList) {
        List<AlarmModel> listAlarm;

        if (!Checker.isEmpty(dataList)) {
            listAlarm = new ArrayList<>();
            for (AlarmEntity alarm : dataList) {
                listAlarm.add(transform(alarm));
            }
        } else {
            listAlarm = new ArrayList<>();
        }

        return listAlarm;
    }

    public AlarmEntity convert(AlarmModel alarmModel) {
        return new AlarmEntity(alarmModel.id, alarmModel.lable, alarmModel.hour, alarmModel.minute, alarmModel.day, ringtoneMapper.convert(alarmModel.ringtone)
                , alarmModel.durationMinute, alarmModel.durationSecond, ringtoneMapper.convert(alarmModel.ringtoneMusic)
                , alarmModel.durationMusicMinute, alarmModel.durationMusicSecond, alarmModel.isWetMode, alarmModel.isEnable);
    }
}
