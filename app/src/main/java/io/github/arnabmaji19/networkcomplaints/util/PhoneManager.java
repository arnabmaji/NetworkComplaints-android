package io.github.arnabmaji19.networkcomplaints.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import io.github.arnabmaji19.networkcomplaints.model.SimInfo;

public class PhoneManager {

    private static final String TAG = "PhoneManager";

    private TelephonyManager telephonyManager;

    public PhoneManager(TelephonyManager telephonyManager) {
        this.telephonyManager = telephonyManager;
    }


    /*
    public String getConnectionType(){
        //Returns Connection type in String

        switch (telephonyManager.getNetworkType()){

            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT"; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA"; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE"; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO_0"; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO_A"; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS"; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA"; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA"; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA"; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS"; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                return "EHRPD"; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                return "EVDO_B"; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                return "HSPAP"; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                return "IDEN"; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                return "LTE"; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_GSM:
                return "GSM";
            // Unknown
            default:
                return "Unknown";
        }
    }
    */


    public List<SimInfo> getSimInfo(Context context) {
        List<SimInfo> simInfoList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                //get list of all available cells
                List<CellInfo> cellInfoList = telephonyManager.getAllCellInfo();
                //get List of all active subscriptions
                SubscriptionManager subscriptionManager = (SubscriptionManager) context.getSystemService(Context.TELEPHONY_SUBSCRIPTION_SERVICE);
                List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                for (int i = 0; i < subscriptionManager.getActiveSubscriptionInfoCount(); i++) {
                    CellInfo cellInfo = cellInfoList.get(i);
                    String cellType;
                    CellSignalStrength cellSignalStrength;

                    if (cellInfo instanceof CellInfoLte) { //determine cell type
                        cellType = "LTE";
                        cellSignalStrength = ((CellInfoLte) cellInfo).getCellSignalStrength();
                    } else if (cellInfo instanceof CellInfoWcdma) {
                        cellType = "WCDMA";
                        cellSignalStrength = ((CellInfoWcdma) cellInfo).getCellSignalStrength();
                    } else if (cellInfo instanceof CellInfoCdma) {
                        cellType = "CDMA";
                        cellSignalStrength = ((CellInfoCdma) cellInfo).getCellSignalStrength();
                    } else { //Determine the network type
                        cellType = "GSM";
                        cellSignalStrength = ((CellInfoGsm) cellInfo).getCellSignalStrength();
                    }
                    //add the sim card info to list
                    SimInfo simInfo = new SimInfo(subscriptionInfoList.get(i).getCarrierName().toString(),
                            cellType, cellSignalStrength.getDbm() + "",
                            cellSignalStrength.getAsuLevel() + "");
                    simInfoList.add(simInfo);

                }
            } catch (Exception e) {
                Toast.makeText(context, "Failed to detect some Sim cards properly!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
        return simInfoList;
    }

}
