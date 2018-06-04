package tran.nam.domain.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.domain.entity.ListRingToneEntity;
import tran.nam.domain.entity.RingToneEntity;
import tran.nam.flatform.model.ListRingTone;
import tran.nam.flatform.model.RingToneData;

@Singleton
public class ListRingToneEntityMapper {

    private RingToneEntityMapper ringToneEntityMapper;

    @Inject
    ListRingToneEntityMapper(RingToneEntityMapper ringToneEntityMapper) {
        this.ringToneEntityMapper = ringToneEntityMapper;
    }

    /**
     * Transform a {@link ListRingTone} into an {@link ListRingToneEntity}.
     *
     * @param data Object to be transformed.
     * @return {@link RingToneEntity}.
     */
    public ListRingToneEntity transform(ListRingTone data) {
        if (data == null || data.ringToneDataList == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ListRingToneEntity entity = new ListRingToneEntity();
        for (RingToneData ringToneData : data.ringToneDataList){
            RingToneEntity ringToneEntity = ringToneEntityMapper.transform(ringToneData);
            entity.addData(ringToneEntity);
        }
        return entity;
    }

    /**
     * Transform a {@link ListRingTone} into an {@link ListRingToneEntity}.
     *
     * @param data Object to be transformed.
     * @return {@link RingToneEntity}.
     */
    public ListRingTone transform(ListRingToneEntity data) {
        if (data == null || data.listRingToneEntities == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        ListRingTone listRingTone = new ListRingTone();
        for (RingToneEntity ringToneEntity : data.listRingToneEntities){
            listRingTone.addData(ringToneEntityMapper.transform(ringToneEntity));
        }
        return listRingTone;
    }
}
