package tran.nam.domain.mapper;

import javax.inject.Inject;

import tran.nam.domain.entity.PreferenceEntity;
import tran.nam.flatform.local.IPreference;

public class PreferenceEntityMapper {

    private RingToneEntityMapper ringToneEntityMapper;

    @Inject
    PreferenceEntityMapper(RingToneEntityMapper ringToneEntityMapper) {
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
        preferenceEntity.defaultRingtone = ringToneEntityMapper.transform(data.getDefaultRingtone());

        return preferenceEntity;
    }
}
