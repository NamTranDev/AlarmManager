package tran.nam.alarmtimer.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.domain.entity.PreferenceEntity;

@Singleton
public class PreferenceMapper {

    private ListRingToneMapper ringtoneMapper;
    private PreferenceModel preferenceModel;

    @Inject
    PreferenceMapper(PreferenceModel preferenceModel, ListRingToneMapper ringtoneMapper) {
        this.preferenceModel = preferenceModel;
        this.ringtoneMapper = ringtoneMapper;
    }

    /**
     * Transform a {@link PreferenceEntity} into an {@link PreferenceModel}.
     *
     * @param data Object to be transformed.
     * @return {@link PreferenceModel}.
     */
    public void transform(PreferenceEntity data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        preferenceModel.is24h = data.is24h;
        preferenceModel.isWetMode = data.isWetMode;
        preferenceModel.defaultRingtone = ringtoneMapper.transform(data.listRingToneEntity);
        preferenceModel.defaultRingtoneMusic = ringtoneMapper.transform(data.listRingToneMusicEntity);
    }

}
