package tran.nam.alarmtimer.di.module;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tran.nam.alarmtimer.application.viewmodel.AddAlarmViewModel;
import tran.nam.alarmtimer.application.viewmodel.AlarmActivityViewModel;
import tran.nam.alarmtimer.application.viewmodel.AlarmViewModel;
import tran.nam.alarmtimer.application.viewmodel.HomeViewModel;
import tran.nam.alarmtimer.application.viewmodel.MainViewModel;
import tran.nam.alarmtimer.application.viewmodel.RingtoneViewModel;
import tran.nam.alarmtimer.application.viewmodel.SettingHomeViewModel;
import tran.nam.alarmtimer.application.viewmodel.SupportHomeViewModel;
import tran.nam.core.di.ViewModelFactory;
import tran.nam.core.di.inject.ViewModelKey;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel.class)
    abstract ViewModel bindActivityMainViewModel(MainViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel bindFragmentHomeViewModel(HomeViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel.class)
    abstract ViewModel bindFragmentAlarmViewModel(AlarmViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SettingHomeViewModel.class)
    abstract ViewModel bindActivitySettingHomeViewModel(SettingHomeViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(SupportHomeViewModel.class)
    abstract ViewModel bindActivitySupportHomeViewModel(SupportHomeViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AddAlarmViewModel.class)
    abstract ViewModel bindActivityAddAlarmViewModel(AddAlarmViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(RingtoneViewModel.class)
    abstract ViewModel bindActivityRingToneViewModel(RingtoneViewModel model);

    @Binds
    @IntoMap
    @ViewModelKey(AlarmActivityViewModel.class)
    abstract ViewModel bindActivityAlarmViewModel(AlarmActivityViewModel model);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
