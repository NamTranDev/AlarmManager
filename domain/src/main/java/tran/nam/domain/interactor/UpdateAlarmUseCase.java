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

public class UpdateAlarmUseCase extends UseCase<Void,AlarmEntity> {

    private DataEntityMapper mapper;

    @Inject UpdateAlarmUseCase(IRespository iAppRepository, SchedulerProvider schedulerProvider,DataEntityMapper mapper) {
        super(iAppRepository, schedulerProvider);
        this.mapper = mapper;
    }

    @Override
    protected Flowable<Void> buildUseCaseFlowable(AlarmEntity alarmEntity) {
        return null;
    }

    @Override
    protected Completable buildUseCaseCompletable(AlarmEntity alarmEntity) {
        return iRepository.updateAlarm(mapper.getAlarmEntityDataMapper().convert(alarmEntity));
    }

    @Override
    protected Observable<Void> buildUseCaseObserve(AlarmEntity alarmEntity) {
        return null;
    }
}
