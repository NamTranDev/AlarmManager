package tran.nam.domain.entity;

import android.net.Uri;

import tran.nam.flatform.model.RingToneData;

public class RingToneEntity {

    public String name;
    public String uri;

    public RingToneEntity() {
    }

    public RingToneEntity(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }
}
