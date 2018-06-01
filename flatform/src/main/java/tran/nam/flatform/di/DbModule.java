package tran.nam.flatform.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tran.nam.flatform.database.AlarmDao;
import tran.nam.flatform.database.DBProvider;

@Module
public class DbModule {

    @Singleton
    @Provides
    DBProvider provideDb(Application app) {
        return Room.databaseBuilder(app, DBProvider.class, "alarm.db").fallbackToDestructiveMigration()
                .build();
    }

}
