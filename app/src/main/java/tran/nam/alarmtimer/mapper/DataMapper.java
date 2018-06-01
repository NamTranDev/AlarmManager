package tran.nam.alarmtimer.mapper;

import javax.inject.Inject;

public class DataMapper {

    private final PreferenceMapper preferenceMapper;
    private final AlarmMapper alarmMapper;

    @Inject DataMapper(PreferenceMapper preferenceMapper, AlarmMapper alarmMapper) {
        this.preferenceMapper = preferenceMapper;
        this.alarmMapper = alarmMapper;
    }

    public PreferenceMapper getPreferenceMapper() {
        return preferenceMapper;
    }

    public AlarmMapper getAlarmMapper() {
        return alarmMapper;
    }
}
