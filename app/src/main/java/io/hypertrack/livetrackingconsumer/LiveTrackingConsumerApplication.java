package io.hypertrack.livetrackingconsumer;

import android.app.Application;

import com.hypertrack.lib.HyperTrack;

/**
 * Created by Aman Jain on 17/05/17.
 */

public class LiveTrackingConsumerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize HyperTrack SDK with the Publishable Key
        // Refer to
        // documentation at https://docs.hypertrack.com/v3/gettingstarted/authentication.html#publishable-key
        // @NOTE: Add **YOUR_PUBLISHABLE_KEY** here for SDK to be authenticated with HyperTrack Server
        HyperTrack.initialize(this,HYPERTRACK_KEY);
    }
}
