package tran.nam.flatform.di;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import tran.nam.flatform.local.IPreference;
import tran.nam.flatform.local.Preference;

@Module
public abstract class PreferenceModule {

    @Binds
    @Singleton
    abstract IPreference bindPreference(Preference preferenceProvider);
}
