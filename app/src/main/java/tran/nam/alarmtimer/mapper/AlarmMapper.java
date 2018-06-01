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

    private RingToneMapper ringtoneMapper;

    @Inject
    public AlarmMapper(RingToneMapper ringtoneMapper) {
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
        final AlarmModel alarmEntity = new AlarmModel();
        alarmEntity.id = data.id;
        alarmEntity.lable = data.lable;
        alarmEntity.hour = data.hour;
        alarmEntity.minute = data.minute;
        alarmEntity.day = data.day;
        alarmEntity.ringtone = ringtoneMapper.transform(data.ringtone);
        alarmEntity.durationMinute = data.durationMinute;
        alarmEntity.durationSecond = data.durationSecond;
        alarmEntity.isEnable = data.isEnable;
        alarmEntity.isWetMode = data.isWetMode;

        return alarmEntity;
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
        return new AlarmEntity(alarmModel.id,alarmModel.lable, alarmModel.hour, alarmModel.minute, alarmModel.day, ringtoneMapper.convert(alarmModel.ringtone)
                , alarmModel.durationMinute,alarmModel.durationSecond, alarmModel.isWetMode, alarmModel.isEnable);
    }
}
