package tran.nam.core.exception;

import android.content.Context;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

import tran.nam.core.R;

@SuppressWarnings("unused")
public final class ErrorMessageFactory {

    @Inject
    ErrorMessageFactory() {
    }

    public String create(Context context, Throwable exception) {
        String error;
        if (exception instanceof UnknownHostException) {
            error = context.getString(R.string.error_no_internet_connection);
        } else if (exception instanceof SocketTimeoutException) {
            error = context.getString(R.string.error_timeout);
        } else if (exception instanceof ConnectException) {
            error = context.getString(R.string.error_connection_refused);
        } else {
            error = context.getString(R.string.error_unknown);
        }
        return error;
    }

}
