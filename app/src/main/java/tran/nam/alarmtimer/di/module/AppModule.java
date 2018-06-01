package tran.nam.alarmtimer.di.module;

import android.app.Application;
import android.app.IntentService;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import tran.nam.alarmtimer.application.view.AppState;
import tran.nam.alarmtimer.application.view.alarm.AddAlarmActivity;
import tran.nam.alarmtimer.application.view.alarm.AlarmActivity;
import tran.nam.alarmtimer.application.view.home.RingToneActivity;
import tran.nam.alarmtimer.application.view.home.SettingHomeActivity;
import tran.nam.alarmtimer.application.view.home.SupportHomeActivity;
import tran.nam.alarmtimer.application.view.main.MainActivity;
import tran.nam.alarmtimer.application.view.splash.SplashActivity;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.controller.OnBootUpAlarmScheduler;
import tran.nam.alarmtimer.di.component.ServiceComponent;
import tran.nam.alarmtimer.di.module.activity.AddAlarmActivityModule;
import tran.nam.alarmtimer.di.module.activity.AlarmActivityModule;
import tran.nam.alarmtimer.di.module.activity.MainActivityModule;
import tran.nam.alarmtimer.di.module.activity.RingToneActivityModule;
import tran.nam.alarmtimer.di.module.activity.SettingHomeActivityModule;
import tran.nam.alarmtimer.di.module.activity.SplashActivityModule;
import tran.nam.alarmtimer.di.module.activity.SupportHomeActivityModule;
import tran.nam.core.di.inject.PerActivity;
import tran.nam.domain.di.DataModule;

/**
 * Provides application-wide dependencies.
 */
@Module(subcomponents = ServiceComponent.class,includes = {
        AndroidSupportInjectionModule.class,
        ViewModelModule.class,
        DataModule.class
})
public abstract class AppModule {

    @Binds
    @Singleton
    /*
     * Singleton annotation isn't necessary since Application instance is unique but is here for
     * convention. In general, providing Activity, Fragment, BroadcastReceiver, etc does not require
     * them to be scoped since they are the components being injected and their instance is unique.
     *
     * However, having a scope annotation makes the module easier to read. We wouldn't have to look
     * at what is being provided in order to understand its scope.
     */
    abstract Application application(AppState app);

    abstract AlarmController alarmController(AppState app);

    /**
     * Provides the injector for the {@link SplashActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity splashActivityInjector();

    /**
     * Provides the injector for the {@link MainActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();

    /**
     * Provides the injector for the {@link SettingHomeActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = SettingHomeActivityModule.class)
    abstract SettingHomeActivity settingHomeActivityInjector();

    /**
     * Provides the injector for the {@link SupportHomeActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = SupportHomeActivityModule.class)
    abstract SupportHomeActivity supportHomeActivityInjector();

    /**
     * Provides the injector for the {@link RingToneActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = RingToneActivityModule.class)
    abstract RingToneActivity ringToneActivityInjector();

    /**
     * Provides the injector for the {@link SupportHomeActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = AddAlarmActivityModule.class)
    abstract AddAlarmActivity addAlarmActivityInjector();

    /**
     * Provides the injector for the {@link SupportHomeActivityModule}, which has access to the dependencies
     * provided by this application instance (singleton scoped objects).
     */
    @PerActivity
    @ContributesAndroidInjector(modules = AlarmActivityModule.class)
    abstract AlarmActivity alarmActivityInjector();
}
