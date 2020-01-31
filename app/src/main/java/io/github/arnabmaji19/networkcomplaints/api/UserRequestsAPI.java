package io.github.arnabmaji19.networkcomplaints.api;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.github.arnabmaji19.networkcomplaints.model.RequestDetails;
import io.github.arnabmaji19.networkcomplaints.util.Session;


///API to get user requests data from server
public class UserRequestsAPI extends API {

    private static final String TAG = "UserRequestsAPI";
    private static final String RELATIVE_URL = "android/requests";

    //status codes
    private static final int STATUS_CODE_SUCCESSFUL = 200;
    private static final int STATUS_CODE_UNSUCCESSFUL = 500;

    private RequestParams params;
    private String url;
    private OnCompleteListener onCompleteListener;

    public UserRequestsAPI() {
        this.params = new RequestParams("user_id", Session.getInstance().getUserId());
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
                //extract request details list from response
                Gson gson = new Gson();
                JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                if (onCompleteListener != null)
                    onCompleteListener.onComplete(statusCode, jsonResponse.getResult());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                if (onCompleteListener != null)
                    onCompleteListener.onComplete(statusCode, null);
            }
        });
    }

    public interface OnCompleteListener {
        void onComplete(int statusCode, List<RequestDetails> requestDetailsList);
    }

    public static class JsonResponse {

        private int response_code;
        private List<RequestDetails> result;

        public JsonResponse() {
        }

        public JsonResponse(int response_code, List<RequestDetails> result) {
            this.response_code = response_code;
            this.result = result;
        }

        public int getResponse_code() {
            return response_code;
        }

        public void setResponse_code(int response_code) {
            this.response_code = response_code;
        }

        public List<RequestDetails> getResult() {
            return result;
        }

        public void setResult(List<RequestDetails> result) {
            this.result = result;
        }
    }
}
