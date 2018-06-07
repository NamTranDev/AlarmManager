package tran.nam.alarmtimer.application.model;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static tran.nam.alarmtimer.type.DateType.FRIDAY;
import static tran.nam.alarmtimer.type.DateType.MONDAY;
import static tran.nam.alarmtimer.type.DateType.SATURDAY;
import static tran.nam.alarmtimer.type.DateType.SUNDAY;
import static tran.nam.alarmtimer.type.DateType.THURSDAY;
import static tran.nam.alarmtimer.type.DateType.TUESDAY;
import static tran.nam.alarmtimer.type.DateType.WEDNESDAY;
import static tran.nam.util.Constant.EMPTY;
import static tran.nam.util.Constant.HOURS_IN_HALF_DAY;

public class AlarmModel extends BaseObservable implements Parcelable {

    public long id;

    public String lable;
    public int hour;
    public int minute;
    public int[] day;
    public ListRingToneModel ringtone;
    public ListRingToneModel ringtoneMusic;
    public long durationMinute;
    public long durationSecond;
    public long durationMusicMinute;
    public long durationMusicSecond;
    public boolean isWetMode;
    public boolean isEnable;
    public boolean is24h;
    public String countdownTimer;
    public boolean isHideAmPM;

    public AlarmModel() {
    }

    public AlarmModel(String lable, int hour, int minute, int[] day, ListRingToneModel ringtone, long durationMinute, long durationSecond
            , ListRingToneModel ringtoneMusic, long durationMusicMinute, long durationMusicSecond, boolean isWetMode, boolean isEnable) {
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

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AlarmModel && ((AlarmModel) obj).id == id;
    }

    public String getDuration() {
        if (durationMinute == 0)
            return String.format("%s sec", durationSecond);
        return String.format("%s min %s sec", durationMinute, durationSecond);
    }

    public String getDurationMusic() {
        if (durationMusicMinute == 0)
            return String.format("%s sec", durationMusicSecond);
        return String.format("%s min %s sec", durationMusicMinute, durationMusicSecond);
    }

    public String getTime() {
        return String.format("%s : %s %s", getCurrentHour() > 9 ? getCurrentHour() : "0" + getCurrentHour(), minute > 9 ? String.valueOf(minute) : "0" + minute, !is24h && !isHideAmPM ? getAmOrPm() : EMPTY);
    }

    /**
     * @return The current hour in the range (0-23).
     */
    private int getCurrentHour() {
        int currentHour = hour;
        if (is24h) {
            return currentHour;
        } else {
            return currentHour == 12 ? currentHour : currentHour % HOURS_IN_HALF_DAY;
        }
    }

    public String getAmOrPm() {
        return hour < 12 ? "AM" : "PM";
    }

    public void setCountdownTimer(long timer) {
        int seconds = (int) ((timer / 1000) % 60);
        int minutes = (int) ((timer / 1000) / 60);
        countdownTimer = String.format("%s : %s", minutes > 9 ? minutes : "0" + minutes, seconds > 9 ? seconds : "0" + seconds);
        notifyChange();
    }

    public String getStringWeek() {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < day.length; i++) {
            switch (day[i]) {
                case SUNDAY:
                    text.append("Sun");
                    break;
                case MONDAY:
                    text.append("Mon");
                    break;
                case TUESDAY:
                    text.append("Tue");
                    break;
                case WEDNESDAY:
                    text.append("Wed");
                    break;
                case THURSDAY:
                    text.append("Thu");
                    break;
                case FRIDAY:
                    text.append("Fri");
                    break;
                case SATURDAY:
                    text.append("Sat");
                    break;
            }
            if (i != day.length - 1)
                text.append(", ");
        }
        return text.toString();
    }

    private boolean isRecurring(int day) {
        checkDay(day);
        for (int i = 0; i < this.day.length; i++) {
            if (this.day[i] == day)
                return true;
        }
        return false;
    }

    private boolean hasRecurrence() {
        return numRecurringDays() > 0;
    }

    private int numRecurringDays() {
        return day.length;
    }

    public long ringsAt() {
        // Always with respect to the current date and time
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long baseRingTime = calendar.getTimeInMillis();

        if (!hasRecurrence()) {
            if (baseRingTime <= System.currentTimeMillis()) {
                // The specified time has passed for today
                baseRingTime += TimeUnit.DAYS.toMillis(1);
            }
            return baseRingTime;
        } else {
            // Compute the ring time just for the next closest recurring day.
            // Remember that day constants defined in the Calendar class are
            // not zero-based like ours, so we have to compensate with an offset
            // of magnitude one, with the appropriate sign based on the situation.
            int weekdayToday = calendar.get(Calendar.DAY_OF_WEEK);
            int numDaysFromToday = -1;

            for (int i = weekdayToday; i <= Calendar.SATURDAY; i++) {
                if (isRecurring(i)) {
                    if (i == weekdayToday) {
                        if (baseRingTime > System.currentTimeMillis()) {
                            // The normal ring time has not passed yet
                            numDaysFromToday = 0;
                            break;
                        }
                    } else {
                        numDaysFromToday = i - weekdayToday;
                        break;
                    }
                }
            }

            // Not computed yet
            if (numDaysFromToday < 0) {
                for (int i = Calendar.SUNDAY; i < weekdayToday; i++) {
                    if (isRecurring(i)) {
                        numDaysFromToday = Calendar.SATURDAY - weekdayToday + i;
                        break;
                    }
                }
            }

            // Still not computed yet. The only recurring day is weekdayToday,
            // and its normal ring time has already passed.
            if (numDaysFromToday < 0 && isRecurring(weekdayToday)
                    && baseRingTime <= System.currentTimeMillis()) {
                numDaysFromToday = 7;
            }

            if (numDaysFromToday < 0)
                throw new IllegalStateException("How did we get here?");

            return baseRingTime + TimeUnit.DAYS.toMillis(numDaysFromToday);
        }
    }

    public List<Integer> getListDate() {
        List<Integer> integers = new ArrayList<>();
        if (day == null || day.length == 0)
            return integers;

        for (int i = 0; i < day.length; i++) {
            integers.add(day[i]);
        }

        return integers;
    }

    private static void checkDay(int day) {
        if (day < Calendar.SUNDAY || day > Calendar.SATURDAY) {
            throw new IllegalArgumentException("Invalid day of week: " + day);
        }
    }

    @Override
    public String toString() {
        return "AlarmModel{" +
                "id=" + id +
                ", lable='" + lable + '\'' +
                ", hour=" + hour +
                ", minute=" + minute +
                ", day=" + Arrays.toString(day) +
                ", ringtone=" + ringtone.toString() +
                ", ringtoneMusic=" + ringtoneMusic.toString() +
                ", durationMinute=" + durationMinute +
                ", durationSecond=" + durationSecond +
                ", durationMusicMinute=" + durationMusicMinute +
                ", durationMusicSecond=" + durationMusicSecond +
                ", isWetMode=" + isWetMode +
                ", isEnable=" + isEnable +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.lable);
        dest.writeInt(this.hour);
        dest.writeInt(this.minute);
        dest.writeIntArray(this.day);
        dest.writeParcelable(this.ringtone, flags);
        dest.writeParcelable(this.ringtoneMusic, flags);
        dest.writeLong(this.durationMinute);
        dest.writeLong(this.durationSecond);
        dest.writeLong(this.durationMusicMinute);
        dest.writeLong(this.durationMusicSecond);
        dest.writeByte(this.isWetMode ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isEnable ? (byte) 1 : (byte) 0);
        dest.writeByte(this.is24h ? (byte) 1 : (byte) 0);
        dest.writeString(this.countdownTimer);
        dest.writeByte(this.isHideAmPM ? (byte) 1 : (byte) 0);
    }

    protected AlarmModel(Parcel in) {
        this.id = in.readLong();
        this.lable = in.readString();
        this.hour = in.readInt();
        this.minute = in.readInt();
        this.day = in.createIntArray();
        this.ringtone = in.readParcelable(ListRingToneModel.class.getClassLoader());
        this.ringtoneMusic = in.readParcelable(ListRingToneModel.class.getClassLoader());
        this.durationMinute = in.readLong();
        this.durationSecond = in.readLong();
        this.durationMusicMinute = in.readLong();
        this.durationMusicSecond = in.readLong();
        this.isWetMode = in.readByte() != 0;
        this.isEnable = in.readByte() != 0;
        this.is24h = in.readByte() != 0;
        this.countdownTimer = in.readString();
        this.isHideAmPM = in.readByte() != 0;
    }

    public static final Creator<AlarmModel> CREATOR = new Creator<AlarmModel>() {
        @Override
        public AlarmModel createFromParcel(Parcel source) {
            return new AlarmModel(source);
        }

        @Override
        public AlarmModel[] newArray(int size) {
            return new AlarmModel[size];
        }
    };
}
