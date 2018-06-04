package tran.nam.alarmtimer.mapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.domain.entity.ListRingToneEntity;
import tran.nam.domain.entity.RingToneEntity;

@Singleton
public class ListRingToneMapper {

    private RingToneMapper ringToneMapper;

    @Inject
    ListRingToneMapper(RingToneMapper ringToneMapper) {
        this.ringToneMapper = ringToneMapper;
    }

    /**
     * Transform a {@link ListRingToneEntity} into an {@link ListRingToneModel}.
     *
     * @param data Object to be transformed.
     * @return {@link ListRingToneEntity}.
     */
    public ListRingToneModel transform(ListRingToneEntity data) {
        if (data == null || data.listRingToneEntities == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final ListRingToneModel listRingToneModel = new ListRingToneModel();
        for (RingToneEntity ringToneEntity : data.listRingToneEntities) {
            listRingToneModel.addData(ringToneMapper.transform(ringToneEntity));
        }
        return listRingToneModel;
    }

    public ListRingToneEntity convert(ListRingToneModel listRingTone) {
        ListRingToneEntity listRingToneEntity = new ListRingToneEntity();
        for (RingToneModel ringToneModel : listRingTone.ringToneModels){
            listRingToneEntity.addData(ringToneMapper.convert(ringToneModel));
        }
        return listRingToneEntity;
    }
}
