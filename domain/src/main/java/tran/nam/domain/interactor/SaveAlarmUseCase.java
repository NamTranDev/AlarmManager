package tran.nam.domain.interactor;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import tran.nam.domain.IRespository;
import tran.nam.domain.entity.AlarmEntity;
import tran.nam.domain.executor.SchedulerProvider;
import tran.nam.domain.interactor.core.UseCase;
import tran.nam.domain.mapper.DataEntityMapper;

public class SaveAlarmUseCase extends UseCase<Long,AlarmEntity> {

    private DataEntityMapper mapper;

    @Inject SaveAlarmUseCase(IRespository iAppRepository, SchedulerProvider schedulerProvider, DataEntityMapper mapper) {
        super(iAppRepository, schedulerProvider);
        this.mapper = mapper;
    }

    @Override
    protected Flowable<Long> buildUseCaseFlowable(AlarmEntity alarmEntity) {
        return iRepository.saveAlarm(mapper.getAlarmEntityDataMapper().convert(alarmEntity));
    }

    @Override
    protected Completable buildUseCaseCompletable(AlarmEntity alarmEntity) {
        return null;
    }

    @Override
    protected Observable<Long> buildUseCaseObserve(AlarmEntity alarmEntity) {
        return null;
    }
}
