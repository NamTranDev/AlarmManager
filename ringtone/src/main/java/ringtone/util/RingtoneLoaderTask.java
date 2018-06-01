/*
 * Copyright 2017 Keval Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance wit
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 *  the specific language governing permissions and limitations under the License.
 */

package ringtone.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import static ringtone.util.RingtoneTypes.TYPE_ALARM;
import static ringtone.util.RingtoneTypes.TYPE_MUSIC;
import static ringtone.util.RingtoneTypes.TYPE_NOTIFICATION;
import static ringtone.util.RingtoneTypes.TYPE_RINGTONE;

public class RingtoneLoaderTask extends AsyncTask<Integer, Void, HashMap<String, Uri>> {

    @NonNull
    private final LoadCompleteListener mListener;

    @SuppressLint("StaticFieldLeak")
    @NonNull
    private final Context mApplication;

    public RingtoneLoaderTask(@NonNull final Context application,
                       @NonNull final LoadCompleteListener loadCompleteListener) {
        mListener = loadCompleteListener;
        mApplication = application;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @SafeVarargs
    @SuppressLint("MissingPermission")
    @Override
    @NonNull
    protected final HashMap<String, Uri> doInBackground(Integer... voids) {
        HashMap<String, Uri> ringTones = new HashMap<>();

        int type = voids[0];

        switch (type) {
            case TYPE_RINGTONE:
                ringTones.putAll(RingtoneUtils.getRingTone(mApplication));
                break;
            case TYPE_ALARM:
                ringTones.putAll(RingtoneUtils.getAlarmTones(mApplication));
                break;
            case TYPE_MUSIC:
                ringTones.putAll(RingtoneUtils.getMusic(mApplication));
                break;
            case TYPE_NOTIFICATION:
                ringTones.putAll(RingtoneUtils.getNotificationTones(mApplication));
                break;
            default:
                throw new IllegalArgumentException("Invalid ringtone type.");
        }
        return ringTones;
    }

    @Override
    protected void onPostExecute(HashMap<String, Uri> ringtone) {
        super.onPostExecute(ringtone);
        mListener.onLoadComplete(ringtone);
    }

    public interface LoadCompleteListener {
        void onLoadComplete(@NonNull final HashMap<String, Uri> ringtone);
    }
}
