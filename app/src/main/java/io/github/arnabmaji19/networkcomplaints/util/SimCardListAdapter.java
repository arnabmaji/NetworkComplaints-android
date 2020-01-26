package io.github.arnabmaji19.networkcomplaints.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.networkcomplaints.R;
import io.github.arnabmaji19.networkcomplaints.model.SimInfo;

public class SimCardListAdapter extends RecyclerView.Adapter<SimCardListAdapter.SimCardViewHolder> {

    private List<SimInfo> simInfoList;

    public SimCardListAdapter(List<SimInfo> simInfoList) {
        this.simInfoList = simInfoList;
    }

    @NonNull
    @Override
    public SimCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate custom layout for list row
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sim_deatils_card, parent, false);
        return new SimCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimCardViewHolder holder, int position) {
        SimInfo simInfo = simInfoList.get(position);
        //populate recycler view with data
        holder.operatorNameTextView.setText(simInfo.getOperatorName());
        holder.networkTypeTextView.setText(simInfo.getNetworkType());
        String signalStrength = simInfo.getSignalStrength() + "dbm";
        holder.signalStrengthTextView.setText(signalStrength);
        holder.asuLevelTextView.setText(simInfo.getAsuLevel());
    }

    @Override
    public int getItemCount() {
        return simInfoList.size();
    }

    public class SimCardViewHolder extends RecyclerView.ViewHolder {

        TextView operatorNameTextView;
        TextView networkTypeTextView;
        TextView signalStrengthTextView;
        TextView asuLevelTextView;

        public SimCardViewHolder(@NonNull View view) {
            super(view);

            //link views
            operatorNameTextView = view.findViewById(R.id.operatorNameTextView);
            networkTypeTextView = view.findViewById(R.id.networkTypeTextView);
            signalStrengthTextView = view.findViewById(R.id.signalStrengthTextView);
            asuLevelTextView = view.findViewById(R.id.asuLevelTextView);
        }
    }
}
