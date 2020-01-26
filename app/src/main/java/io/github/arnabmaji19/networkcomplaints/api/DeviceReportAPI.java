package io.github.arnabmaji19.networkcomplaints.api;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.github.arnabmaji19.networkcomplaints.model.DeviceReport;
import io.github.arnabmaji19.networkcomplaints.util.Session;

public class DeviceReportAPI extends API {

    private static final String TAG = "DeviceReportAPI";
    private static final String RELATIVE_URL = "android/reports";

    private RequestParams params;
    private String url;
    private OnCompleteListener onCompleteListener;

    public DeviceReportAPI(DeviceReport deviceReport) {
        params = new RequestParams();
        params.put("user_id", Session.getInstance().getUserId());
        params.put("device_report", new Gson().toJson(deviceReport));
        url = getUrl(RELATIVE_URL);
    }

    public void addOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }

    @Override
    public void post() {
        client.post(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                if (onCompleteListener != null)
                    onCompleteListener.onComplete(statusCode);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (onCompleteListener != null)
                    onCompleteListener.onComplete(statusCode);
            }
        });

    }

    public interface OnCompleteListener {
        void onComplete(int statusCode);
    }
}
