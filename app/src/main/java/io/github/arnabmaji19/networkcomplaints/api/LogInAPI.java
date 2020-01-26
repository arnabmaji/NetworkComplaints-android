package io.github.arnabmaji19.networkcomplaints.api;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import io.github.arnabmaji19.networkcomplaints.model.User;

public class LogInAPI extends API {

    private static final String TAG = "LogInAPI";
    private static final String RELATIVE_URL = "android/login";

    //status codes
    public static final int STATUS_CODE_SUCCESSFUL = 200;
    public static final int STATUS_CODE_USER_NOT_REGISTERED = 300;
    public static final int STATUS_CODE_INCORRECT_PASSWORD = 350;
    public static final int STATUS_CODE_FAILED = 400;

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
                }
            }

        });
    }

    public interface OnCompleteListener {
        void onComplete(User user);
    }
}
