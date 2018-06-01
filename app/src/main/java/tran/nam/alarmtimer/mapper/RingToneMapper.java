package tran.nam.alarmtimer.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.domain.entity.RingToneEntity;

@Singleton
public class RingToneMapper {

    @Inject
    RingToneMapper() {
    }

    /**
     * Transform a {@link RingToneEntity} into an {@link RingToneModel}.
     *
     * @param data Object to be transformed.
     * @return {@link RingToneModel}.
     */
    public RingToneModel transform(RingToneEntity data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final RingToneModel ringToneModel = new RingToneModel();
        ringToneModel.name = data.name;
        ringToneModel.uri = data.uri;

        return ringToneModel;
    }

    public RingToneEntity convert(RingToneModel ringToneModel){
        return new RingToneEntity(ringToneModel.name,ringToneModel.uri);
    }
}
