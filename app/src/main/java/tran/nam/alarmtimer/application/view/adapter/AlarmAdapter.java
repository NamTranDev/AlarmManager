package tran.nam.alarmtimer.application.view.adapter;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.AlarmModel;
import tran.nam.alarmtimer.application.model.PreferenceModel;
import tran.nam.alarmtimer.databinding.AdapterAlarmBinding;
import tran.nam.common.DataBoundListAdapter;
import tran.nam.util.Objects;

public class AlarmAdapter extends DataBoundListAdapter<AlarmModel, AdapterAlarmBinding> {

    private final DataBindingComponent dataBindingComponent;
    private OnItemClick onItemClick;

    public AlarmAdapter(DataBindingComponent dataBindingComponent, PreferenceModel mPref) {
        this.dataBindingComponent = dataBindingComponent;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    protected AdapterAlarmBinding createBinding(ViewGroup parent) {
        AdapterAlarmBinding biding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_alarm, parent, false, dataBindingComponent);
        biding.getRoot().setOnClickListener((v) -> {
            if (onItemClick != null) {
                onItemClick.onItemClick(biding.getAlarmModel());
            }
        });
        biding.swAlarm.setOnClickListener((v) -> {
            if (onItemClick != null) {
                onItemClick.onItemSwitchClick(biding.getAlarmModel());
            }
        });
        biding.getRoot().setOnLongClickListener(v -> {
            if (onItemClick != null){
                onItemClick.onItemLongClick(biding.getAlarmModel());
                return true;
            }
            return false;
        });
        return biding;
    }

    @Override
    protected void bind(AdapterAlarmBinding binding, AlarmModel item) {
        binding.setAlarmModel(item);
    }

    @Override
    protected boolean areItemsTheSame(AlarmModel oldItem, AlarmModel newItem) {
        return Objects.equals(oldItem.id, newItem.id);
    }

    @Override
    protected boolean areContentsTheSame(AlarmModel oldItem, AlarmModel newItem) {
        return false;
    }

    public interface OnItemClick {
        void onItemClick(AlarmModel alarmModel);
        void onItemSwitchClick(AlarmModel alarmModel);
        void onItemLongClick(AlarmModel alarmModel);
    }
}
