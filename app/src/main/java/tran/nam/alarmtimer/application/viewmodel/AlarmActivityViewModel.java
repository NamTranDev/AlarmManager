package tran.nam.alarmtimer.application.viewmodel;

import android.app.Application;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;

import java.util.Random;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.ListRingToneModel;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.alarmtimer.controller.AlarmController;
import tran.nam.alarmtimer.mapper.DataMapper;
import tran.nam.alarmtimer.util.RingtoneLoop;
import tran.nam.core.model.BaseViewModel;
import tran.nam.domain.interactor.UpdateAlarmUseCase;
import tran.nam.util.Logger;

import static android.content.Context.POWER_SERVICE;
import static android.content.Context.VIBRATOR_SERVICE;

public class AlarmActivityViewModel extends BaseViewModel {

    private AlarmModel alarm;
    private CountDownTimer mTimerSound;
    private CountDownTimer mTimerMusic;
    private OnAlarmCallback onAlarmCallback;
    private RingtoneLoop mRingtone;
    private UpdateAlarmUseCase mUpdateAlarmUseCase;
    private DataMapper mapper;
    private AlarmController mAlarmController;
    private Vibrator mVibrator;
    private PowerManager.WakeLock mWakeLock;
    private RingToneModel ringMusic;
    private boolean isMusic = false;
    private CountDownTimer countDownSound;

    @Inject
    AlarmActivityViewModel(@NonNull Application application, UpdateAlarmUseCase mUpdateAlarmUseCase, DataMapper mapper, AlarmController alarmController) {
        super(application);
        this.mUpdateAlarmUseCase = mUpdateAlarmUseCase;
        this.mapper = mapper;
        this.mAlarmController = alarmController;
        mRingtone = new RingtoneLoop(application);
        mVibrator = (Vibrator) application.getSystemService(VIBRATOR_SERVICE);
        PowerManager powerManager = (PowerManager) application.getSystemService(POWER_SERVICE);
        assert powerManager != null;
        mWakeLock = powerManager.newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
    }

    public AlarmModel setAlarm(AlarmModel alarm, boolean vibrator) {
        this.alarm = alarm;
        this.alarm.isHideAmPM = true;
        this.alarm.countdownTimer = String.format("%s:%s", String.valueOf(this.alarm.durationMinute), "00");
        // TODO: 6/1/18 Play random
        ListRingToneModel listRingToneModel = this.alarm.ringtone;
        if (listRingToneModel != null && listRingToneModel.ringToneModels != null && listRingToneModel.ringToneModels.size() > 0) {
            int random = new Random().nextInt(listRingToneModel.ringToneModels.size());
            RingToneModel ringToneModel = listRingToneModel.ringToneModels.get(random);
            mRingtone.play(Uri.parse(ringToneModel.uri));
        }
        createCountDownSound();
        ListRingToneModel listRingToneMusicModel = this.alarm.ringtoneMusic;
        if (listRingToneMusicModel != null && listRingToneMusicModel.ringToneModels != null && listRingToneMusicModel.ringToneModels.size() > 0) {
            int random = new Random().nextInt(listRingToneMusicModel.ringToneModels.size());
            ringMusic = listRingToneMusicModel.ringToneModels.get(random);
        }
        return this.alarm;
    }

    public void setOnAlarmCallback(OnAlarmCallback onAlarmCallback) {
        this.onAlarmCallback = onAlarmCallback;
    }

    private void createCountDownSound() {
        long timeSound = alarm.durationMinute * 60 * 1000 + alarm.durationSecond * 1000;
        Logger.debug("Time Sound : " + timeSound);
        long timeMusic = alarm.durationMusicMinute * 60 * 1000 + alarm.durationMusicSecond * 1000;
        Logger.debug("Time Music : " + timeMusic);
        countDownSound = new CountDownTimer(timeSound,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                isMusic = true;
            }
        };
        countDownSound.start();
        mTimerSound = new CountDownTimer(
                timeSound + timeMusic, 1000) {
            @Override
            public void onTick(long l) {
                Logger.debug("Time : " + l);
                alarm.setCountdownTimer(l);
                if (isMusic && ringMusic != null){
                    isMusic = false;
                    mRingtone.stop();
                    mRingtone.play(Uri.parse(ringMusic.uri));
                }
            }

            @Override
            public void onFinish() {
                if (onAlarmCallback != null)
                    onAlarmCallback.onFinishCountDown();
            }
        };
        mTimerSound.start();
    }

    @Override
    public void detach() {
        if (mTimerSound != null)
            mTimerSound.cancel();
        if (countDownSound != null)
            countDownSound.cancel();
        if (mRingtone != null)
            mRingtone.stop();
        if (mVibrator != null) {
            mVibrator.cancel();
        }
        onCleared();
    }

    public void checkScheduleAlarm() {
        if (alarm.day.length == 0) {
            alarm.isEnable = false;
            mUpdateAlarmUseCase.execute(new UpdateAlarmResponse(), mapper.getAlarmMapper().convert(alarm));
        } else {
            mAlarmController.cancelAlarm(alarm);
            mAlarmController.scheduleAlarm(alarm);
            if (onAlarmCallback != null)
                onAlarmCallback.onFinish();
        }

    }

    private final class UpdateAlarmResponse extends DisposableCompletableObserver {

        @Override
        protected void onStart() {
            super.onStart();
        }

        @Override
        public void onError(Throwable t) {
            Logger.debug(t);
        }

        @Override
        public void onComplete() {
            mAlarmController.cancelAlarm(alarm);
            if (onAlarmCallback != null)
                onAlarmCallback.onFinish();
        }
    }

    public interface OnAlarmCallback {
        void onFinishCountDown();

        void onFinish();
    }
}
