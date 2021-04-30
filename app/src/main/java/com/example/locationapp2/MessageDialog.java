package com.example.locationapp2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.DialogFragment;

// Emily Haigh and Avani Sonawane

public class MessageDialog extends DialogFragment {
    public interface ClickListener {
        public void onClick();
    }

    private ClickListener listener;
    private String message;
    private String latlng;

    public MessageDialog(){};

    public MessageDialog(String message, String latlng){
        this.message = message;
        this.latlng = latlng;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        message = getArguments().getString("message");
        latlng = getArguments().getString("punchline");
        builder.setTitle(message).setMessage(latlng)
                .setNegativeButton("Cancel", null);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
        outstate.putString("message", message);
    }
}

