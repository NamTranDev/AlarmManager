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

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static ringtone.util.RingtoneTypes.TYPE_ALARM;
import static ringtone.util.RingtoneTypes.TYPE_MUSIC;
import static ringtone.util.RingtoneTypes.TYPE_NOTIFICATION;
import static ringtone.util.RingtoneTypes.TYPE_RINGTONE;

@Retention(RetentionPolicy.SOURCE)
@IntDef({
        TYPE_RINGTONE,
        TYPE_ALARM,
        TYPE_MUSIC,
        TYPE_NOTIFICATION
})
public @interface RingtoneTypes {
    int TYPE_RINGTONE = 1;
    int TYPE_ALARM = 2;
    int TYPE_MUSIC = 3;
    int TYPE_NOTIFICATION = 4;
}
