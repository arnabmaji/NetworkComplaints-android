package io.github.arnabmaji19.networkcomplaints.api;

import android.util.Log;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LogInAPI extends API {

    private static final String TAG = "LogInAPI";
    private static final String RELATIVE_URL = "android/login";

    //status codes
    public static final int STATUS_CODE_SUCCESSFUL = 200;
    public static final int STATUS_CODE_USER_NOT_REGISTERED = 404;
    public static final int STATUS_CODE_INCORRECT_PASSWORD = 401;
    public static final int STATUS_CODE_FAILED = 500;

    private RequestParams params;
    private String url;
    private OnCompleteListener onCompleteListener;

    public LogInAPI(String email, String password) {
        //create RequestParams
        this.params = new RequestParams();
        this.params.put("email", email); //put email
        this.params.put("password", password); //put password

        this.url = getUrl(RELATIVE_URL);

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
                if (onCompleteListener != null) {
                    //send the user object
                    //TODO: call onComplete method
                    Log.d(TAG, "onSuccess: " + response.toString());
                    String userId = "unknown";
                    String username = "unknown";
                    try {
                        userId = response.getString("user_id");
                        username = response.getString("name");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (onCompleteListener != null)
                        onCompleteListener.onComplete(statusCode, userId, username);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d(TAG, "onFailure: " + statusCode);
                if (onCompleteListener != null)
                    onCompleteListener.onComplete(statusCode, null, null);
            }
        });
    }

    public interface OnCompleteListener {
        void onComplete(int statusCode, String userId, String username);
    }
}
