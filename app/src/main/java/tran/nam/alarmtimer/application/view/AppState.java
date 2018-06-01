package tran.nam.alarmtimer.application.view;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import tran.nam.alarmtimer.controller.OnBootUpAlarmScheduler;
import tran.nam.alarmtimer.di.component.AppComponent;
import tran.nam.alarmtimer.di.component.DaggerAppComponent;
import tran.nam.alarmtimer.di.component.ServiceComponent;
import tran.nam.alarmtimer.di.module.OnBootUpAlarmSchedulerModule;

public class AppState extends Application implements Application.ActivityLifecycleCallbacks, HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public ServiceComponent getServiceInjector(OnBootUpAlarmScheduler onBootUpAlarmScheduler){
        return appComponent.serviceBuilder().withServiceModule(new OnBootUpAlarmSchedulerModule(onBootUpAlarmScheduler)).build();
    }
}
