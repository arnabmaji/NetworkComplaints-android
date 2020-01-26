package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.networkcomplaints.model.SimInfo;
import io.github.arnabmaji19.networkcomplaints.util.LayoutToggler;
import io.github.arnabmaji19.networkcomplaints.util.PermissionsUtil;
import io.github.arnabmaji19.networkcomplaints.util.PhoneManager;
import io.github.arnabmaji19.networkcomplaints.util.SimCardListAdapter;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private Activity activity;
    private ConstraintLayout permissionsLayout;
    private ConstraintLayout dashoardLayout;
    private ConstraintLayout localDataAlertLayout;
    private Button grantPermissionsButton;
    private Button analyzeButton;
    private LayoutToggler layoutToggler;
    private PermissionsUtil permissionsUtil;
    private RecyclerView simDetailsRecyclerView;
    private PhoneManager phoneManager;

    public DashboardFragment(Activity activity) {
        this.activity = activity;
        this.permissionsUtil = new PermissionsUtil(activity, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        layoutToggler = new LayoutToggler();
        //set up PhoneManager
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        phoneManager = new PhoneManager(telephonyManager);
        //link views
        grantPermissionsButton = view.findViewById(R.id.grantPermissionsButton);
        analyzeButton = view.findViewById(R.id.analyzeButton);
        permissionsLayout = view.findViewById(R.id.permissionsLayout);
        dashoardLayout = view.findViewById(R.id.dashboardLayout);
        localDataAlertLayout = view.findViewById(R.id.localDatAlertLayout);
        simDetailsRecyclerView = view.findViewById(R.id.simCardListRecyclerView);
        simDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        //add layouts to layout toggler
        layoutToggler.addLayouts(permissionsLayout, dashoardLayout);

        if (permissionsUtil.areAllPermissionsGranted()) {
            layoutToggler.setVisible(localDataAlertLayout);
        } else {
            layoutToggler.setVisible(permissionsLayout);
        }

        grantPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionsUtil.askPermissions();
            }
        });

        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SimInfo> simInfoList = phoneManager.getSimInfo(activity.getBaseContext());
                SimCardListAdapter simCardListAdapter = new SimCardListAdapter(simInfoList);
                simDetailsRecyclerView.setAdapter(simCardListAdapter);
                layoutToggler.setVisible(dashoardLayout);
            }
        });

        return view;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (requestCode == PermissionsUtil.PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionsUtil.areAllPermissionsGranted()) {
                    layoutToggler.setVisible(dashoardLayout);
                }
            }
        }
    }
}
