package tran.nam.flatform.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import tran.nam.flatform.R;

public class ListRingTone {

    public List<RingToneData> ringToneDataList;

    public ListRingTone getDefault(Application application){
        RingToneData ringToneData = new RingToneData(application.getString(R.string.title_default_list_item), "R.raw.bell");
        ringToneDataList = new ArrayList<>();
        ringToneDataList.add(ringToneData);
        return this;
    }

    public void addData(RingToneData ringToneData){
        if (ringToneDataList == null)
            ringToneDataList = new ArrayList<>();

        ringToneDataList.add(ringToneData);
    }
}
