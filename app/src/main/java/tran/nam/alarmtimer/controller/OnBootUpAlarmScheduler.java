/*
 * Copyright 2017 Phillip Hsu
 *
 * This file is part of ClockPlus.
 *
 * ClockPlus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ClockPlus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with ClockPlus.  If not, see <http://www.gnu.org/licenses/>.
 */

package tran.nam.alarmtimer.controller;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.JobIntentService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.subscribers.DisposableSubscriber;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.view.AppState;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.interactor.GetListAlarmUseCase;
import tran.nam.util.Logger;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class OnBootUpAlarmScheduler extends JobIntentService {

    static final int JOB_ID = 1000;

    @Inject
    AlarmController mAlarmController;

    @Inject
    GetListAlarmUseCase mGetListAlarmUseCase;

    @Inject
    DataMapper mapper;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, OnBootUpAlarmScheduler.class, JOB_ID, work);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ((AppState)getApplicationContext()).getServiceInjector(this).inject(this);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        Logger.debug("OnBootUpAlarmScheduler" , "mGetListAlarmUseCase != null : " + mGetListAlarmUseCase != null);
        mGetListAlarmUseCase.execute(new GetListAlarm(),  mGetListAlarmUseCase.iRepository.getSetting().isWetMode ? 2 : 1);
    }

    private final class GetListAlarm extends DisposableSubscriber<List<AlarmEntity>> {

        @Override
        protected void onStart() {
            super.onStart();
            Logger.debug("OnBootUpAlarmScheduler" , "onStart");
        }

        @Override
        public void onNext(List<AlarmEntity> alarmEntities) {
            Logger.debug("OnBootUpAlarmScheduler" , "onNext");
            List<AlarmModel> alarmModels = mapper.getAlarmMapper().transform(alarmEntities);
            for (AlarmModel alarmModel : alarmModels) {
                Logger.debug("OnBootUpAlarmScheduler : List : ",alarmModel.toString() + "\n");
                if (alarmModel.isEnable){
                    Logger.debug("OnBootUpAlarmScheduler + alarm Choose",alarmModel.toString());
                    mAlarmController.scheduleAlarm(alarmModel);
                }
            }
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
        }

        @Override
        public void onComplete() {
            Logger.debug("OnBootUpAlarmScheduler" , "onComplete");
        }
    }
}
