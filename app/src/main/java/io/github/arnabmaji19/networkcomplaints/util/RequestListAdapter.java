package io.github.arnabmaji19.networkcomplaints.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.networkcomplaints.R;
import io.github.arnabmaji19.networkcomplaints.model.RequestDetails;

public class RequestListAdapter extends RecyclerView.Adapter<RequestListAdapter.RequestViewHolder> {

    private Context context;
    private List<RequestDetails> requestDetailsList;

    public RequestListAdapter(Context context, List<RequestDetails> requestDetailsList) {
        this.context = context;
        this.requestDetailsList = requestDetailsList;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate custom layout for list row
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.user_request_card, parent, false);
        return new RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        //set texts in each row
        final RequestDetails details = requestDetailsList.get(position);
        holder.operatorNameTextView.setText(details.getOperatorName());
        holder.networkTypeTextView.setText(details.getNetworkType());
        holder.signalStrengthTextView.setText(details.getStrength());
        holder.submissionDateTextView.setText(details.getSubmissionDate());
        //set progress status image view a/c to progress
        if (details.getStatus() == 1) {
            holder.progressStatusImageView.setImageResource(R.drawable.done);
        } else {
            holder.progressStatusImageView.setImageResource(R.drawable.not_done);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestDetailsDialog dialog = new RequestDetailsDialog(context, details);
                dialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return requestDetailsList.size();
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        TextView operatorNameTextView;
        TextView networkTypeTextView;
        TextView signalStrengthTextView;
        TextView submissionDateTextView;
        ImageView progressStatusImageView;

        public RequestViewHolder(@NonNull View view) {
            super(view);

            //link views
            operatorNameTextView = view.findViewById(R.id.rqOperatorNameTextView);
            networkTypeTextView = view.findViewById(R.id.rqNetworkTypeTextView);
            signalStrengthTextView = view.findViewById(R.id.rqSignalStrengthTextView);
            submissionDateTextView = view.findViewById(R.id.rqDateOfSubmissionTextView);
            progressStatusImageView = view.findViewById(R.id.rqProgressStatusImageView);
        }
    }
}
