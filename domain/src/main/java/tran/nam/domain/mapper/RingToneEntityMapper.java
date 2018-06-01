package tran.nam.domain.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.domain.entity.RingToneEntity;
import tran.nam.flatform.model.RingToneData;

@Singleton
public class RingToneEntityMapper {

    @Inject
    RingToneEntityMapper() {
    }

    /**
     * Transform a {@link RingToneData} into an {@link RingToneEntity}.
     *
     * @param data Object to be transformed.
     * @return {@link RingToneEntity}.
     */
    public RingToneEntity transform(RingToneData data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final RingToneEntity ringToneEntity = new RingToneEntity();
        ringToneEntity.name = data.name;
        ringToneEntity.uri = data.uri;

        return ringToneEntity;
    }
}
