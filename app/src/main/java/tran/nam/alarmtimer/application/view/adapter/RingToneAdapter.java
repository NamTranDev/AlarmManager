package tran.nam.alarmtimer.application.view.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.alarmtimer.databinding.AdapterRingToneBinding;
import tran.nam.common.DataBoundListAdapter;

public class RingToneAdapter extends DataBoundListAdapter<RingToneModel, AdapterRingToneBinding> {

    private OnItemRingToneClick onItemRingToneClick;

    @Inject
    RingToneAdapter() {
    }

    public void setOnItemRingToneClick(OnItemRingToneClick onItemRingToneClick) {
        this.onItemRingToneClick = onItemRingToneClick;
    }

    @Override
    protected AdapterRingToneBinding createBinding(ViewGroup parent) {
        AdapterRingToneBinding biding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_ring_tone, parent, false);
        biding.getRoot().setOnClickListener(v -> {
            if (onItemRingToneClick != null) {
                onItemRingToneClick.onItemRingToneClick(biding.getRingTone(), Objects.requireNonNull(items).indexOf(biding.getRingTone()));
            }
        });
        return biding;
    }

    public List<RingToneModel> getListChoose() {
        List<RingToneModel> ringToneModels = new ArrayList<>();
        if (null != items) {
            for (RingToneModel ringToneModel : items) {
                if (ringToneModel.isChoose)
                    ringToneModels.add(ringToneModel);
            }
        }
        return ringToneModels;
    }

    public void noneChoose() {
        if (null != items) {
            for (RingToneModel ringToneModel : items) {
                ringToneModel.isChoose = false;
            }
        }
    }

    @Override
    protected void bind(AdapterRingToneBinding binding, RingToneModel item) {
        binding.setRingTone(item);
    }

    @Override
    protected boolean areItemsTheSame(RingToneModel oldItem, RingToneModel newItem) {
        return false;
    }

    @Override
    protected boolean areContentsTheSame(RingToneModel oldItem, RingToneModel newItem) {
        return false;
    }

    public void clearNoneChoose() {
        if (null != items) {
            items.get(0).isChoose = false;
            notifyItemChanged(0);
        }
    }

    public interface OnItemRingToneClick {
        void onItemRingToneClick(RingToneModel item, int position);
    }
}
