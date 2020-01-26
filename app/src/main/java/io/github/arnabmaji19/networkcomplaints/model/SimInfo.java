package io.github.arnabmaji19.networkcomplaints.model;

public class SimInfo {

    private String operatorName;
    private String networkType;
    private String signalStrength;
    private String asuLevel;

    public SimInfo() {
    }

    public SimInfo(String operatorName, String networkType, String signalStrength, String asuLevel) {
        this.operatorName = operatorName;
        this.networkType = networkType;
        this.signalStrength = signalStrength;
        this.asuLevel = asuLevel;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getSignalStrength() {
        return signalStrength;
    }

    public void setSignalStrength(String signalStrength) {
        this.signalStrength = signalStrength;
    }

    public String getAsuLevel() {
        return asuLevel;
    }

    public void setAsuLevel(String asuLevel) {
        this.asuLevel = asuLevel;
    }
}
