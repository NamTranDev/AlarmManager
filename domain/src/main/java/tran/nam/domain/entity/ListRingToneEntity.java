package tran.nam.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class ListRingToneEntity {
    public List<RingToneEntity> listRingToneEntities;

    public void addData(RingToneEntity ringToneEntity){
        if (listRingToneEntities == null)
            listRingToneEntities = new ArrayList<>();

        listRingToneEntities.add(ringToneEntity);
    }
}
