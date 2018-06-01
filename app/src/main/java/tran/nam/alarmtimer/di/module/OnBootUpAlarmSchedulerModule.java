package tran.nam.alarmtimer.di.module;

import dagger.Module;
import dagger.Provides;
import tran.nam.alarmtimer.controller.OnBootUpAlarmScheduler;

@Module
public class OnBootUpAlarmSchedulerModule {

    private OnBootUpAlarmScheduler onBootUpAlarmScheduler;

    public OnBootUpAlarmSchedulerModule(OnBootUpAlarmScheduler onBootUpAlarmScheduler){
        this.onBootUpAlarmScheduler = onBootUpAlarmScheduler;
    }

    @Provides
    public OnBootUpAlarmScheduler provideServiceContext(){
        return onBootUpAlarmScheduler;
    }
}
