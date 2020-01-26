package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import io.github.arnabmaji19.networkcomplaints.util.LayoutToggler;
import io.github.arnabmaji19.networkcomplaints.util.PermissionsUtil;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private ConstraintLayout permissionsLayout;
    private ConstraintLayout dashoardLayout;
    private Button grantPermissionsButton;
    private LayoutToggler layoutToggler;
    private PermissionsUtil permissionsUtil;

    public DashboardFragment(Activity activity) {
        this.permissionsUtil = new PermissionsUtil(activity, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        layoutToggler = new LayoutToggler();
        //link views
        grantPermissionsButton = view.findViewById(R.id.grantPermissionsButton);
        permissionsLayout = view.findViewById(R.id.permissionsLayout);
        dashoardLayout = view.findViewById(R.id.dashboardLayout);
        //add layouts to layout toggler
        layoutToggler.addLayouts(permissionsLayout, dashoardLayout);

        if (permissionsUtil.areAllPermissionsGranted()) {
            layoutToggler.setVisible(dashoardLayout);
        } else {
            layoutToggler.setVisible(permissionsLayout);
        }

        grantPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permissionsUtil.askPermissions();
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
