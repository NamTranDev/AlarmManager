package tran.nam.alarmtimer.application.view.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import tran.nam.alarmtimer.R;
import tran.nam.alarmtimer.databinding.AdapterRingToneBinding;
import tran.nam.alarmtimer.application.model.RingToneModel;
import tran.nam.common.DataBoundListAdapter;

public class RingToneAdapter extends DataBoundListAdapter<RingToneModel,AdapterRingToneBinding>{

    private OnItemRingToneClick onItemRingToneClick;

    @Inject RingToneAdapter() {}

    public void setOnItemRingToneClick(OnItemRingToneClick onItemRingToneClick) {
        this.onItemRingToneClick = onItemRingToneClick;
    }

    @Override
    protected AdapterRingToneBinding createBinding(ViewGroup parent) {
        AdapterRingToneBinding biding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_ring_tone, parent, false);
        biding.getRoot().setOnClickListener(v -> {
            if (onItemRingToneClick != null){
                onItemRingToneClick.onItemRingToneClick(biding.getRingTone(), Objects.requireNonNull(items).indexOf(biding.getRingTone()));
            }
        });
        return biding;
    }

    public List<RingToneModel> getListChoose(){
        List<RingToneModel> ringToneModels = new ArrayList<>();
        if (items != null){
            for (RingToneModel ringToneModel : items){
                if (ringToneModel.isChoose)
                    ringToneModels.add(ringToneModel);
            }
        }
        return ringToneModels;
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

    public interface OnItemRingToneClick{
        void onItemRingToneClick(RingToneModel item, int position);
    }
}
