package tran.nam.alarmtimer.application.view.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        biding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemRingToneClick != null){
                    onItemRingToneClick.onItemRingToneClick(biding.getRingTone());
                }
            }
        });
        return biding;
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
        void onItemRingToneClick(RingToneModel item);
    }
}
