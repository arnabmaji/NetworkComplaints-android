package io.github.arnabmaji19.networkcomplaints.model;

public class LocationData {

    private String latitude;
    private String longitude;
    private String postalCode;
    private String state;

    public LocationData() {
    }

    public LocationData(String latitude, String longitude, String postalCode, String state) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.postalCode = postalCode;
        this.state = state;
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

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
