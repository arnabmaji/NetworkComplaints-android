package io.github.arnabmaji19.networkcomplaints.model;

import java.util.List;

public class DeviceReport {

    private LocationData locationData;
    private List<SimInfo> simInfoList;

    public DeviceReport() {
    }

    public DeviceReport(LocationData locationData, List<SimInfo> simInfoList) {
        this.locationData = locationData;
        this.simInfoList = simInfoList;
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
