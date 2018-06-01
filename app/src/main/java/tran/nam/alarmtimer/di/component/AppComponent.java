package tran.nam.alarmtimer.di.component;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import tran.nam.alarmtimer.application.view.AppState;
import tran.nam.alarmtimer.di.module.AppModule;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent extends AndroidInjector<AppState> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(AppState appState);
        AppComponent build();
    }

    ServiceComponent.Builder serviceBuilder();
}
