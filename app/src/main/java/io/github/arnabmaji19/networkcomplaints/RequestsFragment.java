package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.arnabmaji19.networkcomplaints.api.UserRequestsAPI;
import io.github.arnabmaji19.networkcomplaints.model.RequestDetails;
import io.github.arnabmaji19.networkcomplaints.util.LayoutToggler;
import io.github.arnabmaji19.networkcomplaints.util.RequestListAdapter;
import io.github.arnabmaji19.networkcomplaints.util.Session;

public class RequestsFragment extends Fragment {


    private Activity activity;
    private LayoutToggler layoutToggler;

    private ConstraintLayout loadRequestsLayout;
    private ConstraintLayout userRequestsLayout;
    private RecyclerView requestsRecyclerView;
    private LinearLayout loadingBar;
    private Button sendRequestButton;

    public RequestsFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        //link views
        loadRequestsLayout = view.findViewById(R.id.loadRequestsPromptLayout);
        userRequestsLayout = view.findViewById(R.id.userRequestsLayout);
        requestsRecyclerView = view.findViewById(R.id.requestsRecyclerView);
        requestsRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        requestsRecyclerView.addItemDecoration(new DividerItemDecoration(requestsRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        loadingBar = view.findViewById(R.id.loadingProgress);
        sendRequestButton = view.findViewById(R.id.getRequestsButton);

        layoutToggler = new LayoutToggler();
        layoutToggler.addLayouts(loadRequestsLayout, userRequestsLayout);

        //set loadRequestsLayout by default
        layoutToggler.setVisible(loadRequestsLayout);
        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Session.getInstance().isSessionAvailable()) { //If session is not available, user is in offline mode
                    Toast.makeText(activity.getBaseContext(), "Oops! You are in offline mode", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingBar.setVisibility(View.VISIBLE); //show the loading bar
                UserRequestsAPI userRequestsAPI = new UserRequestsAPI();
                userRequestsAPI.addOnCompleteListener(new UserRequestsAPI.OnCompleteListener() {
                    @Override
                    public void onComplete(int statusCode, List<RequestDetails> requestDetailsList) {
                        RequestListAdapter requestListAdapter = new RequestListAdapter(activity, requestDetailsList);
                        requestsRecyclerView.setAdapter(requestListAdapter);
                        layoutToggler.setVisible(userRequestsLayout);
                    }
                });
                userRequestsAPI.post();
            }
        });


        return view;
    }
}
