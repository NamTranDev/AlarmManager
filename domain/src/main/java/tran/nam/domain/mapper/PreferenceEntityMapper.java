package tran.nam.domain.mapper;

import javax.inject.Inject;

import tran.nam.domain.entity.PreferenceEntity;
import tran.nam.flatform.local.IPreference;

public class PreferenceEntityMapper {

    private ListRingToneEntityMapper ringToneEntityMapper;

    @Inject
    PreferenceEntityMapper(ListRingToneEntityMapper ringToneEntityMapper) {
        this.ringToneEntityMapper = ringToneEntityMapper;
    }

    /**
     * Transform a {@link IPreference} into an {@link PreferenceEntity}.
     *
     * @param data Object to be transformed.
     * @return {@link PreferenceEntity}.
     */
    public PreferenceEntity transform(IPreference data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final PreferenceEntity preferenceEntity = new PreferenceEntity();
        preferenceEntity.is24h = data.isAlarm24h();
        preferenceEntity.isWetMode = data.isWetMode();
        preferenceEntity.listRingToneEntity = ringToneEntityMapper.transform(data.getListDefaultRingtone());
        preferenceEntity.listRingToneMusicEntity = ringToneEntityMapper.transform(data.getListDefaultRingtoneMusic());

        return preferenceEntity;
    }
}
