package com.pemikir.flappy2048;

import android.app.Application;
import android.provider.Settings;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by siddharth on 10/4/2015.
 */
public class Flappy2048 extends Application {

    public static final String APPLICATION_ID = "N3pkWuwT67PW4hHkrjX29soOXv7wz3KY9sRDuCRd";
    public static final String CLIENT_KEY = "yc8oY5E1rze9uaXJCDxp5qfGN4Xevs4Yj45iUsHQ";

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Parse.enableLocalDatastore(this);
            Parse.initialize(this, APPLICATION_ID, CLIENT_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
