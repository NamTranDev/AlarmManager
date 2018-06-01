package tran.nam.core.state;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static tran.nam.core.state.Status.ERROR;
import static tran.nam.core.state.Status.LOADING_DIALOG;
import static tran.nam.core.state.Status.LOADING_NONE;
import static tran.nam.core.state.Status.LOADING_NORMAL;
import static tran.nam.core.state.Status.SUCCESS;

/**
 * Status of a resource that is provided to the UI.
 * <p>
 * These are usually created by the Repository classes where they return
 * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
 */

@IntDef({LOADING_NONE, LOADING_DIALOG, LOADING_NORMAL, SUCCESS, ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface Status {
    int LOADING_NONE = 1;
    int LOADING_DIALOG = 2;
    int LOADING_NORMAL = 3;
    int SUCCESS = 4;
    int ERROR = 5;
}
