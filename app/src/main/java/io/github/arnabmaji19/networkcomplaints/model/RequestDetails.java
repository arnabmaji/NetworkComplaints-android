package io.github.arnabmaji19.networkcomplaints.model;

public class RequestDetails {

    private String id;
    private String userid;
    private String latitude;
    private String longitude;
    private String asuLevel;
    private String strength;
    private String networkType;
    private String operatorName;
    private String submissionDate;
    private int status;

    public RequestDetails() {
    }

    public RequestDetails(String id, String userid, String latitude, String longitude, String asuLevel, String strength, String networkType, String operatorName, String submissionDate, int status) {
        this.id = id;
        this.userid = userid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.asuLevel = asuLevel;
        this.strength = strength;
        this.networkType = networkType;
        this.operatorName = operatorName;
        this.submissionDate = submissionDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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

    public String getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(String asuLevel) {
        this.asuLevel = asuLevel;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
