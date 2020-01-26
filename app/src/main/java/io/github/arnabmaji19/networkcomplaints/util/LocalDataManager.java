package io.github.arnabmaji19.networkcomplaints.util;
//Saves and retrieves data locally on mobile

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import io.github.arnabmaji19.networkcomplaints.model.DeviceReport;

public class LocalDataManager {

    private static final String FILENAME_WITH_EXTENSION = "device_report.sm";
    private static final String TAG = "LocalDataManager";
    private Gson gson;

    private File file;

    public LocalDataManager(Activity activity) {
        file = new File(activity.getFilesDir(), FILENAME_WITH_EXTENSION);
        this.gson = new Gson();
    }

    //Convert LocalScheduleData object into Json String and save as text file
    public void saveDeviceReport(DeviceReport deviceReport) {
        String objectJson = gson.toJson(deviceReport);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(objectJson);
        } catch (IOException e) {
            Log.e(TAG, "saveLocalScheduleData: Failed to save local schedule data" + e.getMessage());
        }
    }

    //Convert json string to LocalScheduleData object from files directory
    public DeviceReport retrieveDeviceReport() {
        DeviceReport deviceReport = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            deviceReport = gson.fromJson(builder.toString(), DeviceReport.class);
        } catch (IOException e) {
            Log.e(TAG, "retrieveLocalScheduleData: Failed to retrieve local schedule data" + e.getMessage());
        }
        return deviceReport;
    }

    public boolean isLocalDataAvailable() {
        return file.exists();
    }

    public boolean clearLocalData() {
        return file.delete();
    }
}
