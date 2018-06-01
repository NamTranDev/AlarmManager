package tran.nam.alarmtimer.application.model;

import android.os.Parcel;
import android.os.Parcelable;

import tran.nam.domain.entity.RingToneEntity;

public class RingToneModel implements Parcelable{

    public String name;
    public String uri;
    public boolean isChoose;

    public RingToneModel() {
    }

    public RingToneModel(String name, String uri) {
        this.name = name;
        this.uri = uri;
    }

    protected RingToneModel(Parcel in) {
        name = in.readString();
        uri = in.readString();
        isChoose = in.readByte() != 0;
    }

    public static final Creator<RingToneModel> CREATOR = new Creator<RingToneModel>() {
        @Override
        public RingToneModel createFromParcel(Parcel in) {
            return new RingToneModel(in);
        }

        @Override
        public RingToneModel[] newArray(int size) {
            return new RingToneModel[size];
        }
    };

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RingToneModel && ((RingToneModel) obj).name.equals(name) && ((RingToneModel) obj).uri.equals(uri);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(uri);
        dest.writeByte((byte) (isChoose ? 1 : 0));
    }

    @Override
    public String toString() {
        return "RingToneModel{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", isChoose=" + isChoose +
                '}';
    }
}
