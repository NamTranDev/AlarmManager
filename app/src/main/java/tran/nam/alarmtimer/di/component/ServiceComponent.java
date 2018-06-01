package tran.nam.alarmtimer.di.component;

import dagger.Subcomponent;
import tran.nam.alarmtimer.controller.OnBootUpAlarmScheduler;
import tran.nam.alarmtimer.di.module.OnBootUpAlarmSchedulerModule;

@Subcomponent(modules = OnBootUpAlarmSchedulerModule.class)
public interface ServiceComponent {
    @Subcomponent.Builder
    public interface Builder {
        Builder withServiceModule(OnBootUpAlarmSchedulerModule onBootUpAlarmSchedulerModule);
        ServiceComponent build();
    }

    void inject(OnBootUpAlarmScheduler onBootUpAlarmScheduler);
}
