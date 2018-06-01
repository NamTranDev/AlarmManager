package tran.nam.flatform.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import tran.nam.flatform.model.AlarmData;

@Database(entities = {AlarmData.class}, version = 1 ,exportSchema = false)
public abstract class DBProvider extends RoomDatabase{
    public abstract AlarmDao alarmDao();
}
