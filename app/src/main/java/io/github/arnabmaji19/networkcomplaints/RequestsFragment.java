package io.github.arnabmaji19.networkcomplaints;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.github.arnabmaji19.networkcomplaints.util.LayoutToggler;

public class RequestsFragment extends Fragment {


    private Activity activity;
    private LayoutToggler layoutToggler;

    private ConstraintLayout loadRequestsLayout;
    private ConstraintLayout userRequestsLayout;
    private RecyclerView requestsRecyclerView;

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

        layoutToggler = new LayoutToggler();
        layoutToggler.addLayouts(loadRequestsLayout, userRequestsLayout);

        return view;
    }
}
