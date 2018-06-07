/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package tran.nam.domain.mapper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import tran.nam.domain.entity.AlarmEntity;
import tran.nam.flatform.model.AlarmData;
import tran.nam.flatform.model.ListRingTone;
import tran.nam.util.Checker;

/**
 * Mapper class used to transform {@link AlarmData} (in the domain layer)
 * to {@link AlarmEntity} in the
 * presentation layer.
 */
public class AlarmEntityDataMapper {

    private ListRingToneEntityMapper ringToneEntityMapper;

    @Inject
    public AlarmEntityDataMapper(ListRingToneEntityMapper ringToneEntityMapper) {
        this.ringToneEntityMapper = ringToneEntityMapper;
    }

    /**
     * Transform a {@link AlarmData} into an {@link AlarmEntity}.
     *
     * @param data Object to be transformed.
     * @return {@link AlarmEntity}.
     */
    public AlarmEntity transform(AlarmData data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final AlarmEntity alarmEntity = new AlarmEntity();
        alarmEntity.id = data.id;
        alarmEntity.lable = data.lable;
        alarmEntity.hour = data.hour;
        alarmEntity.minute = data.minute;
        alarmEntity.day = data.getDate();
        alarmEntity.listRingToneEntity = ringToneEntityMapper.transform(data.ringToneData());
        alarmEntity.listRingToneMusicEntity = ringToneEntityMapper.transform(data.ringToneMusicData());
        alarmEntity.durationMinute = data.durationMinute;
        alarmEntity.durationSecond = data.durationSecond;
        alarmEntity.durationMusicMinute = data.durationMusicMinute;
        alarmEntity.durationMusicSecond = data.durationMusicSecond;
        alarmEntity.isEnable = data.isEnable;
        alarmEntity.isWetMode = data.isWetMode;

        return alarmEntity;
    }

    /**
     * Transform a Collection of {@link AlarmData} into a Collection of {@link AlarmEntity}.
     *
     * @param dataList Objects to be transformed.
     * @return List of {@link AlarmEntity}.
     */
    public List<AlarmEntity> transform(List<AlarmData> dataList) {
        List<AlarmEntity> listAlarm;

        if (!Checker.isEmpty(dataList)) {
            listAlarm = new ArrayList<>();
            for (AlarmData alarm : dataList) {
                listAlarm.add(transform(alarm));
            }
        } else {
            listAlarm = new ArrayList<>();
        }

        return listAlarm;
    }

    public AlarmData convert(AlarmEntity alarmEntity) {
        return new AlarmData(alarmEntity.id, alarmEntity.lable, alarmEntity.hour, alarmEntity.minute, Arrays.toString(alarmEntity.day)
                , new Gson().toJson(ringToneEntityMapper.transform(alarmEntity.listRingToneEntity), ListRingTone.class), alarmEntity.durationMinute, alarmEntity.durationSecond
                , new Gson().toJson(ringToneEntityMapper.transform(alarmEntity.listRingToneMusicEntity), ListRingTone.class), alarmEntity.durationMusicMinute, alarmEntity.durationMusicSecond
                , alarmEntity.isWetMode, alarmEntity.isEnable);
    }
}
