package tran.nam.flatform.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.Gson;

import java.util.List;

import tran.nam.util.Logger;

@Entity(tableName = "AlarmData")
public class AlarmData {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String lable;
    public int hour;
    public int minute;
    public String day;
    public String ringtone;
    public String ringtoneMusic;
    public long durationMinute;
    public long durationSecond;
    public long durationMusicMinute;
    public long durationMusicSecond;
    public boolean isWetMode;
    public boolean isEnable;

    public AlarmData(long id, String lable, int hour, int minute, String day, String ringtone, long durationMinute, long durationSecond
            , String ringtoneMusic, long durationMusicMinute, long durationMusicSecond, boolean isWetMode, boolean isEnable) {
        this.id = id;
        this.lable = lable;
        this.hour = hour;
        this.minute = minute;
        this.day = day;
        this.ringtone = ringtone;
        this.ringtoneMusic = ringtoneMusic;
        this.durationMinute = durationMinute;
        this.durationSecond = durationSecond;
        this.durationMusicMinute = durationMusicMinute;
        this.durationMusicSecond = durationMusicSecond;
        this.isWetMode = isWetMode;
        this.isEnable = isEnable;
    }

    public ListRingTone ringToneData() {
        return new Gson().fromJson(ringtone, ListRingTone.class);
    }

    public ListRingTone ringToneMusicData() {
        return new Gson().fromJson(ringtoneMusic, ListRingTone.class);
    }

    public int[] getDate() {
        try {
            String[] stringDay = day.split("[\\[\\]]")[1].split(", ");
            int number[] = new int[stringDay.length];

            for (int i = 0; i < stringDay.length; i++) {
                number[i] = Integer.parseInt(stringDay[i]);
            }
            return number;
        }catch (Exception e){
            Logger.debug(e);
        }
        return new int[]{};
    }
}
