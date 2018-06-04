package tran.nam.alarmtimer.application.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class ListRingToneModel implements Parcelable {

    public List<RingToneModel> ringToneModels;

    public void addData(RingToneModel ringToneModel) {
        if (ringToneModels == null)
            ringToneModels = new ArrayList<>();

        ringToneModels.add(ringToneModel);
    }

    public void addData(List<RingToneModel> ringToneModel) {
        if (ringToneModels == null)
            ringToneModels = new ArrayList<>();

        ringToneModels.addAll(ringToneModel);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.ringToneModels);
    }

    public ListRingToneModel() {
    }

    protected ListRingToneModel(Parcel in) {
        this.ringToneModels = in.createTypedArrayList(RingToneModel.CREATOR);
    }

    public static final Creator<ListRingToneModel> CREATOR = new Creator<ListRingToneModel>() {
        @Override
        public ListRingToneModel createFromParcel(Parcel source) {
            return new ListRingToneModel(source);
        }

        @Override
        public ListRingToneModel[] newArray(int size) {
            return new ListRingToneModel[size];
        }
    };
}
