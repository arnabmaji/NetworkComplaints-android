package io.github.arnabmaji19.networkcomplaints.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import io.github.arnabmaji19.networkcomplaints.R;
import io.github.arnabmaji19.networkcomplaints.model.RequestDetails;

public class RequestDetailsDialog extends Dialog {

    private RequestDetails requestDetails;

    public RequestDetailsDialog(@NonNull Context context, RequestDetails requestDetails) {
        super(context);
        this.requestDetails = requestDetails;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_request_detail);

        //link and update text views of the dialog
        ((TextView) findViewById(R.id.dialogRequestIdTextView)).setText(requestDetails.getId());
        ((TextView) findViewById(R.id.dialogSubmissionDateTextView)).setText(requestDetails.getSubmissionDate());
        ((TextView) findViewById(R.id.dialogLatitudeTextView)).setText(requestDetails.getLatitude());
        ((TextView) findViewById(R.id.dialogLongitudeTextView)).setText(requestDetails.getLongitude());
        ((TextView) findViewById(R.id.dialogOperatorNameTextView)).setText(requestDetails.getOperatorName());
        ((TextView) findViewById(R.id.dialogNetworkTypeTextView)).setText(requestDetails.getNetworkType());
        String signalStrength = requestDetails.getStrength() + " dbm";
        ((TextView) findViewById(R.id.dialogSignalStrengthTextView)).setText(signalStrength);
        ((TextView) findViewById(R.id.dialogAsuLevelTextView)).setText(requestDetails.getAsuLevel());

        ImageView progressStatusImageView = findViewById(R.id.dialogProgressStatusImageView);
        if (requestDetails.getStatus() == 0) {
            progressStatusImageView.setImageResource(R.drawable.not_done);
        } else {
            progressStatusImageView.setImageResource(R.drawable.done);
        }

    }
}
