package io.github.arnabmaji19.networkcomplaints.model;

import java.util.List;

public class DeviceReport {

    private String userEmail;
    private LocationData locationData;
    private List<SimData> simDataList;

    public DeviceReport() {
    }

    public DeviceReport(String userEmail, LocationData locationData, List<SimData> simDataList) {
        this.userEmail = userEmail;
        this.locationData = locationData;
        this.simDataList = simDataList;
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

    public List<SimData> getSimDataList() {
        return simDataList;
    }

    public void setSimDataList(List<SimData> simDataList) {
        this.simDataList = simDataList;
    }
}
