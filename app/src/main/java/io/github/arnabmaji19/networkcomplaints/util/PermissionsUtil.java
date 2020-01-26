package io.github.arnabmaji19.networkcomplaints.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PermissionsUtil {

    private static final String[] permissionsNeeded = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_PHONE_STATE};
    public static final int PERMISSIONS_REQUEST_CODE = 1;

    private Activity activity;
    private Fragment fragment;

    public PermissionsUtil(Activity activity, Fragment fragment) {
        this.activity = activity;
        this.fragment = fragment;
    }

    public boolean areAllPermissionsGranted() {
        for (String permission : permissionsNeeded) {
            if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void askPermissions() {
        fragment.requestPermissions(permissionsNeeded, PERMISSIONS_REQUEST_CODE);
    }
}
