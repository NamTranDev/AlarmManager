package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.ToolbarModel;
import tran.nam.core.model.BaseViewModel;

public class SupportHomeViewModel extends BaseViewModel {

    public ToolbarModel toolbarModel;

    @Inject
    SupportHomeViewModel(@NonNull Application application) {
        super(application);
        toolbarModel = new ToolbarModel(true, application.getString(R.string.title_support_home), R.drawable.ic_back);
    }

    @Override
    public void detach() {

    }
}
