package tran.nam.domain.entity;

public class AlarmEntity {

    public long id;
    public String lable;
    public int hour;
    public int minute;
    public int[] day;
    public ListRingToneEntity listRingToneEntity;
    public long durationMinute;
    public long durationSecond;
    public boolean isWetMode;
    public boolean isEnable;

    public AlarmEntity() {
    }

    public AlarmEntity(long id, String lable, int hour, int minute, int[] day, ListRingToneEntity listRingToneEntity, long durationMinute, long durationSecond, boolean isWetMode, boolean isEnable) {
        this.id = id;
        this.lable = lable;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.listRingToneEntity = listRingToneEntity;
        this.durationMinute = durationMinute;
        this.durationSecond = durationSecond;
        this.isWetMode = isWetMode;
        this.isEnable = isEnable;
    }
}
