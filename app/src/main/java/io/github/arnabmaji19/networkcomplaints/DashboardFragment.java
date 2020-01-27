package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.networkcomplaints.api.DeviceReportAPI;
import io.github.arnabmaji19.networkcomplaints.model.DeviceReport;
import io.github.arnabmaji19.networkcomplaints.model.LocationData;
import io.github.arnabmaji19.networkcomplaints.model.SimInfo;
import io.github.arnabmaji19.networkcomplaints.util.LayoutToggler;
import io.github.arnabmaji19.networkcomplaints.util.LocalDataManager;
import io.github.arnabmaji19.networkcomplaints.util.LocationDataManager;
import io.github.arnabmaji19.networkcomplaints.util.PermissionsUtil;
import io.github.arnabmaji19.networkcomplaints.util.PhoneManager;
import io.github.arnabmaji19.networkcomplaints.util.Session;
import io.github.arnabmaji19.networkcomplaints.util.SimCardListAdapter;
import io.github.arnabmaji19.networkcomplaints.util.WaitDialog;

public class DashboardFragment extends Fragment {

    private static final String TAG = "DashboardFragment";

    private Activity activity;
    private ConstraintLayout permissionsLayout;
    private ConstraintLayout dashoardLayout;
    private ConstraintLayout localDataAlertLayout;
    private LinearLayout loadingLayout;
    private TextView latitudeTextView;
    private TextView longitudeTextView;
    private Button grantPermissionsButton;
    private Button analyzeButton;
    private Button sendDataButton;
    private LayoutToggler layoutToggler;
    private WaitDialog waitDialog;
    private PermissionsUtil permissionsUtil;
    private RecyclerView simDetailsRecyclerView;
    private PhoneManager phoneManager;
    private LocationDataManager locationDataManager;
    private LocalDataManager localDataManager;
    private DeviceReport deviceReport;
    private DeviceReportAPI deviceReportAPI;

    public DashboardFragment(Activity activity) {
        this.activity = activity;
        this.permissionsUtil = new PermissionsUtil(activity, this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        layoutToggler = new LayoutToggler();
        //set up PhoneManager
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        phoneManager = new PhoneManager(telephonyManager);
        LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        locationDataManager = new LocationDataManager(activity, locationManager);
        localDataManager = new LocalDataManager(activity);

        //link views
        grantPermissionsButton = view.findViewById(R.id.grantPermissionsButton);
        analyzeButton = view.findViewById(R.id.analyzeButton);
        sendDataButton = view.findViewById(R.id.sendDataButton);
        permissionsLayout = view.findViewById(R.id.permissionsLayout);
        dashoardLayout = view.findViewById(R.id.dashboardLayout);
        localDataAlertLayout = view.findViewById(R.id.localDatAlertLayout);
        loadingLayout = view.findViewById(R.id.laodingBar);
        latitudeTextView = view.findViewById(R.id.latitudeTextView);
        longitudeTextView = view.findViewById(R.id.longitudeTextView);
        simDetailsRecyclerView = view.findViewById(R.id.simCardListRecyclerView);
        simDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        waitDialog = new WaitDialog(activity);
        //add layouts to layout toggler
        layoutToggler.addLayouts(permissionsLayout, dashoardLayout);

        if (permissionsUtil.areAllPermissionsGranted()) { //check if all permissions are granted
            if (localDataManager.isLocalDataAvailable()) { //if local data is available show data
                deviceReport = localDataManager.retrieveDeviceReport();
                updateDeviceReportInfo(deviceReport);
                initializeDeviceReportAPI(deviceReport); //initialize the api
                layoutToggler.setVisible(dashoardLayout); //show dashboard layout
            } else {
                layoutToggler.setVisible(localDataAlertLayout); //else show no local data is available
            }
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
            public void onClick(final View view) {
                if (!locationDataManager.isGPSEnabled()) { //if gps is not turned on do not request location updates
                    Toast.makeText(activity.getBaseContext(), "Please turn on GPS", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingLayout.setVisibility(View.VISIBLE); //show the loading bar
                //get location details
                locationDataManager.requestCurrentLocation(new LocationDataManager.OnSuccessListener() {
                    @Override
                    public void onSuccess(Location location) {
                        loadingLayout.setVisibility(View.GONE); //hide the loading layout

                        //create device report
                        String latitude = location.getLatitude() + "";
                        String longitude = location.getLongitude() + "";
                        List<SimInfo> simInfoList = phoneManager.getSimInfo(activity.getBaseContext());
                        LocationData locationData = new LocationData(latitude, longitude);
                        deviceReport = new DeviceReport(locationData, simInfoList);

                        //update views for user
                        updateDeviceReportInfo(deviceReport);


                        initializeDeviceReportAPI(deviceReport);
                        layoutToggler.setVisible(dashoardLayout); //show the dashboard layout
                    }
                });
            }
        });

        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Session.getInstance().isSessionAvailable()) { //if session is available send data
                    deviceReportAPI.addOnCompleteListener(new DeviceReportAPI.OnCompleteListener() {
                        @Override
                        public void onComplete(int statusCode) {
                            String responseMessage = "";
                            if (statusCode == DeviceReportAPI.STATUS_CODE_SUCCESSFUL) {
                                responseMessage = "Thank you for your submission!";
                            } else if (statusCode == DeviceReportAPI.STATUS_CODE_UNSUCCESSFUL) {
                                responseMessage = "Something went wrong!";
                            }

                            Toast.makeText(activity.getBaseContext(), responseMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                    deviceReportAPI.post();
                    return;
                }

                //If session is not available, prompt the user to save device report
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder
                        .setTitle("Save Device Report")
                        .setMessage("Looks like you are in offline Mode." +
                                "\nWould you like to save device report?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //save device report for future use
                                localDataManager = new LocalDataManager(activity);
                                localDataManager.saveDeviceReport(deviceReport);

                                Toast.makeText(activity.getBaseContext(), "Device report saved!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;

    }

    private void updateDeviceReportInfo(DeviceReport deviceReport) {
        //get sim card info list
        SimCardListAdapter simCardListAdapter = new SimCardListAdapter(deviceReport.getSimInfoList());
        simDetailsRecyclerView.setAdapter(simCardListAdapter); //populate recycler view

        //update location details
        latitudeTextView.setText(deviceReport.getLocationData().getLatitude());
        longitudeTextView.setText(deviceReport.getLocationData().getLongitude());
    }

    private void initializeDeviceReportAPI(DeviceReport deviceReport) {
        if (Session.getInstance().isSessionAvailable()) { //if session is available, configure api to send data
            deviceReportAPI = new DeviceReportAPI(deviceReport);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult: ");
        if (requestCode == PermissionsUtil.PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (permissionsUtil.areAllPermissionsGranted()) {
                    layoutToggler.setVisible(localDataAlertLayout);
                }
            }
        }
    }
}
