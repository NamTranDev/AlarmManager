package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.core.model.BaseViewModel;

public class MainViewModel extends BaseViewModel {

    public ToolbarModel toolbarModel;

    @Inject
    MainViewModel(@NonNull Application application) {
        super(application);
        toolbarModel = new ToolbarModel(false, application.getString(R.string.title_main), R.drawable.ic_more);
    }

    @Override
    public void detach() {
    }
}
