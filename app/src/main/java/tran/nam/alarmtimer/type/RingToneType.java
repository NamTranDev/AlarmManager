package tran.nam.alarmtimer.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import static tran.nam.alarmtimer.type.RingToneType.MUSIC;
import static tran.nam.alarmtimer.type.RingToneType.TONE;

@IntDef({TONE, MUSIC})
@Retention(RetentionPolicy.SOURCE)
public @interface RingToneType {
    int TONE = 1;
    int MUSIC = 2;
}
