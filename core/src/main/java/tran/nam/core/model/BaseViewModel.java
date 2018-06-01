package tran.nam.core.model;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

public abstract class BaseViewModel extends AndroidViewModel {

    protected Application application;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public abstract void detach();
}
