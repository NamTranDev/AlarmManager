package tran.nam.domain.interactor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import tran.nam.domain.IRespository;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.executor.SchedulerProvider;
import tran.nam.domain.interactor.core.UseCase;

public class GetListAlarmUseCase extends UseCase<List<AlarmEntity>,Integer> {

    @Inject GetListAlarmUseCase(IRespository iAppRepository, SchedulerProvider schedulerProvider) {
        super(iAppRepository, schedulerProvider);
    }

    @Override
    protected Flowable<List<AlarmEntity>> buildUseCaseFlowable(Integer type) {
        return iRepository.fetchAlarm(type);
    }

    @Override
    protected Completable buildUseCaseCompletable(Integer aBoolean) {
        return null;
    }

    @Override
    protected Observable<List<AlarmEntity>> buildUseCaseObserve(Integer aBoolean) {
        return null;
    }
}
