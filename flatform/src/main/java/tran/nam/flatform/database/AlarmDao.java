package tran.nam.flatform.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import tran.nam.flatform.model.AlarmData;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface AlarmDao {

    @Query("SELECT * FROM alarmdata WHERE isWetMode = :isWetMode")
    Flowable<List<AlarmData>> fetchAlarm(boolean isWetMode);

    @Query("SELECT * FROM alarmdata")
    Flowable<List<AlarmData>> fetchAlarm();

    @Insert(onConflict = IGNORE)
    long insert(AlarmData alarm);

    @Update()
    void update(AlarmData data);

    @Delete
    void delete(AlarmData alarmData);
}
