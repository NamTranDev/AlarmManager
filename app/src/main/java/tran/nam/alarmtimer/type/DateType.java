package tran.nam.alarmtimer.type;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static tran.nam.alarmtimer.type.DateType.FRIDAY;
import static tran.nam.alarmtimer.type.DateType.MONDAY;
import static tran.nam.alarmtimer.type.DateType.SATURDAY;
import static tran.nam.alarmtimer.type.DateType.SUNDAY;
import static tran.nam.alarmtimer.type.DateType.THURSDAY;
import static tran.nam.alarmtimer.type.DateType.TUESDAY;
import static tran.nam.alarmtimer.type.DateType.WEDNESDAY;

@IntDef({SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY})
@Retention(RetentionPolicy.SOURCE)
public @interface DateType {
    int SUNDAY = 1;
    int MONDAY = 2;
    int TUESDAY = 3;
    int WEDNESDAY = 4;
    int THURSDAY = 5;
    int FRIDAY = 6;
    int SATURDAY = 7;
}