package tran.nam.domain.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import tran.nam.domain.IRespository;
import tran.nam.domain.Respository;
import tran.nam.domain.executor.AppSchedulerProvider;
import tran.nam.domain.executor.SchedulerProvider;
import tran.nam.flatform.di.DbModule;
import tran.nam.flatform.di.PreferenceModule;

@Module(includes = {PreferenceModule.class, DbModule.class})
public abstract class DataModule {

    @Binds
    @Singleton
    abstract IRespository provideDataManager(Respository repository);

    @Binds
    @Singleton
    abstract SchedulerProvider provideSchedulerProvider(AppSchedulerProvider schedulerProvider);

}
