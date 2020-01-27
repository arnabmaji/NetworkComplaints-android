package io.github.arnabmaji19.networkcomplaints.model;

public class LocationData {

    private String latitude;
    private String longitude;

    public LocationData() {
    }

    public LocationData(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
