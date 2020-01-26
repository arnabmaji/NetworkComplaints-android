package io.github.arnabmaji19.networkcomplaints.model;

import java.util.List;

public class DeviceReport {

    private String userEmail;
    private LocationData locationData;
    private List<SimInfo> simInfoList;

    public DeviceReport() {
    }

    public DeviceReport(String userEmail, LocationData locationData, List<SimInfo> simInfoList) {
        this.userEmail = userEmail;
        this.locationData = locationData;
        this.simInfoList = simInfoList;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocationData getLocationData() {
        return locationData;
    }

    public void setLocationData(LocationData locationData) {
        this.locationData = locationData;
    }

    public List<SimInfo> getSimInfoList() {
        return simInfoList;
    }

    public void setSimInfoList(List<SimInfo> simInfoList) {
        this.simInfoList = simInfoList;
    }
}
