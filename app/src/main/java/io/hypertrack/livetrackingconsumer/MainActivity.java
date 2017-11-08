package io.hypertrack.livetrackingconsumer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hypertrack.lib.HyperTrack;
import com.hypertrack.lib.callbacks.HyperTrackCallback;
import com.hypertrack.lib.models.ErrorResponse;
import com.hypertrack.lib.models.SuccessResponse;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private EditText actionID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    //Initialize views
    private void initUI() {

        actionID = (EditText) findViewById(R.id.action_id);
        Button trackAction = (Button) findViewById(R.id.track);

        //Click listener for trackAction Button
        trackAction.setOnClickListener(trackActionButtonListener);

        Button trackActionWithCallbacks = (Button) findViewById(R.id.track_callback);

        trackActionWithCallbacks.setOnClickListener(trackActionCallbacksButtonListener);

        initializeProgressDialog();
    }

    private View.OnClickListener trackActionButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //Get Action ID from your server to track the order.
            // Currently, here we are enter the action ID of delivery action that is need to be
            // tracked.
            if (actionID.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter action id", Toast.LENGTH_LONG).show();
                return;
            }

            if (progressDialog != null) {
                progressDialog.show();
            }

            ArrayList<String> actions = new ArrayList<>();
            actions.add(actionID.getText().toString());

            // Call trackAction API method with action ID for tracking.
            // Start YourMapActivity containing HyperTrackMapFragment view with the
            // customization on succes response of trackAction method
            // Refer here for more detail https://docs.hypertrack.com/usecases/livetracking/android/installing.html#step-4-track-actions
            HyperTrack.trackAction(actions, new HyperTrackCallback() {
                @Override
                public void onSuccess(@NonNull SuccessResponse response) {

                    if (progressDialog != null) {
                        progressDialog.cancel();
                    }

                    //Start Activity containing HyperTrackMapFragment
                    Intent intent = new Intent(MainActivity.this, YourMapActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {
                    if (progressDialog != null) {
                        progressDialog.cancel();
                    }

                    Toast.makeText(MainActivity.this, "Error Occurred while trackActions: " +
                            errorResponse.getErrorMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    };

    private View.OnClickListener trackActionCallbacksButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Get Action ID from your server to track the order.
            // Currently, here we are enter the action ID of delivery action that is need to be
            // tracked.
            if (actionID.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this, "Enter action id", Toast.LENGTH_LONG).show();
                return;
            }

            if (progressDialog != null) {
                progressDialog.show();
            }

            ArrayList<String> actions = new ArrayList<>();
            actions.add(actionID.getText().toString());
            // Call trackAction API method with action ID for tracking.
            // Start YourMapActivity containing HyperTrackMapFragment view with the
            // customization on succes response of trackAction method
            // Refer here for more detail https://docs.hypertrack.com/usecases/livetracking/android/installing.html#step-4-track-actions
            HyperTrack.trackAction(actions, new HyperTrackCallback() {
                @Override
                public void onSuccess(@NonNull SuccessResponse response) {

                    if (progressDialog != null) {
                        progressDialog.cancel();
                    }

                    //Start Activity containing MapBoxActivity
                    Intent intent = new Intent(MainActivity.this, MapBoxActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onError(@NonNull ErrorResponse errorResponse) {
                    if (progressDialog != null) {
                        progressDialog.cancel();
                    }

                    Toast.makeText(MainActivity.this, "Error Occurred while trackActions: " +
                            errorResponse.getErrorMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Refer here for more detail https://docs.hypertrack.com/usecases/livetracking/android/installing.html#step-7-remove-actions
        HyperTrack.removeActions(null);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void initializeProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
    }
}
