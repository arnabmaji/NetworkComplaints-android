package io.github.arnabmaji19.networkcomplaints.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;

import io.github.arnabmaji19.networkcomplaints.R;

public class WaitDialog {

    private Activity activity;
    private AlertDialog dialog;

    public WaitDialog(Activity activity) {
        this.activity = activity;

        //configure dialog
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        final View dialogView = LayoutInflater.from(activity).inflate(R.layout.progress_dialog, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(dialogView)
                .setCancelable(false);
        this.dialog = builder.create(); //Create dialog
    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }
}
