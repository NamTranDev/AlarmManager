package tran.nam.domain.mapper;

import javax.inject.Inject;

public class DataEntityMapper {

    private final AlarmEntityDataMapper alarmEntityDataMapper;

    @Inject
    DataEntityMapper(AlarmEntityDataMapper alarmEntityDataMapper) {
        this.alarmEntityDataMapper = alarmEntityDataMapper;
    }

    public AlarmEntityDataMapper getAlarmEntityDataMapper() {
        return alarmEntityDataMapper;
    }
}
