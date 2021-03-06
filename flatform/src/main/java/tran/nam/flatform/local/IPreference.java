package tran.nam.flatform.local;

import android.net.Uri;
import android.util.Pair;

import java.util.Map;

import tran.nam.flatform.model.ListRingTone;
import tran.nam.flatform.model.RingToneData;

public interface IPreference {

    boolean isAlarm24h();
    void setAlarm24h(boolean is24h);

    boolean isWetMode();
    void setWetMode(boolean isWetMode);

    ListRingTone getListDefaultRingtone();
    void setListDefaultRingTone(ListRingTone listDefaultRingtone);

    ListRingTone getListDefaultRingtoneMusic();
    void setListDefaultRingToneMusic(ListRingTone listDefaultRingtoneMusic);
}
