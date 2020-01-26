package io.github.arnabmaji19.networkcomplaints.util;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.List;


public class LayoutToggler {
    private List<ConstraintLayout> layouts;
    private ConstraintLayout visibleLayout;

    public void addLayouts(ConstraintLayout... layout) {
        layouts = Arrays.asList(layout);
        //Hide all layouts at first
        for (ConstraintLayout constraintLayout : layouts) {
            constraintLayout.setVisibility(View.GONE);
        }
    }

    public void setVisible(ConstraintLayout layoutToVisible) {
        if (visibleLayout != null)
            visibleLayout.setVisibility(View.GONE); //Hide the visible layout
        layoutToVisible.setVisibility(View.VISIBLE); //Show the new layout
        visibleLayout = layoutToVisible; //set the new layout to be the visible layout
    }
}
