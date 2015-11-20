package com.pemikir.flappy2048;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.parse.Parse;
import com.parse.ParseInstallation;

import java.io.IOException;

import info.hoang8f.widget.FButton;

public class SplashActivity extends AppCompatActivity {


    FButton rate, play, howtoplay;
    GoogleCloudMessaging gcm;
    Context context;
    String regId;
    Animation animFadein;
    Animation animFadeinsplash;
    ImageView splash_logo;

    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    static final String TAG = "Register Activity";
    ParseInstallation installation;
    //    InterstitialAd adverd;
    AdView mAdViewupper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_splash);
        context = getApplicationContext();
        AdView mAdView = (AdView) findViewById(R.id.adView);
//        mAdView.setAdUnitId(getString(R.string.banner_ad_unit_id));

        mAdViewupper = (AdView) findViewById(R.id.adViewupper);
//        mAdViewupper.setAdUnitId(getString(R.string.banner_ad_unit_id));

        try {
            if (Utils.isConnectingToInternet(context)) {
                installation = ParseInstallation.getCurrentInstallation();
                mAdView.loadAd(new AdRequest.Builder().build());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        animFadeinsplash = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadeinsplash);

        rate = (FButton) findViewById(R.id.rate);
        play = (FButton) findViewById(R.id.play);
        howtoplay = (FButton) findViewById(R.id.howtoplay);
        splash_logo = (ImageView) findViewById(R.id.splash_logo);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificationSound();
                if (Utils.isConnectingToInternet(SplashActivity.this)) {
//                    addDetailInBackend();
                    AppRateLink();

                } else {
                    Snackbar.make(view, "Check Your Internet Connection", Snackbar.LENGTH_LONG).setAction("Ok", null).show();
                }

            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationSound();
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
        });

        howtoplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Comming Soon", Toast.LENGTH_SHORT).show();
                notificationSound();
                startActivity(new Intent(SplashActivity.this, HowToPlay.class));


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.isConnectingToInternet(SplashActivity.this)) {
//            registerGCM();
            addDetailInBackend();
            mAdViewupper.loadAd(new AdRequest.Builder().build());
//            adverd = Utils.newInterstitialAd(getApplicationContext());
            AdRequest adRequest = new AdRequest.Builder()
                    .setRequestAgent("android_studio:ad_template").build();
//            adverd.loadAd(adRequest);
            Log.i("Start", "Start");
        }

        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override
            public void run() {

                splash_logo.setVisibility(View.INVISIBLE);
                play.setAnimation(animFadein);
                rate.setAnimation(animFadein);
                splash_logo.setAnimation(animFadeinsplash);
                howtoplay.setAnimation(animFadein);

                play.setVisibility(View.VISIBLE);
                rate.setVisibility(View.VISIBLE);
                splash_logo.setVisibility(View.VISIBLE);
                howtoplay.setVisibility(View.VISIBLE);

            }
        }, 3 * 1000);

    }

    public void addDetailInBackend() {
        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.i("Flappy", android_id);
        try {
            BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
            String deviceName = myDevice.getName();
            installation.put("Unique_Id", android_id);
            installation.put("ModelName", android.os.Build.MODEL);
            installation.put("deviceName", deviceName);

            installation.saveInBackground();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void AppRateLink() {
        Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
        }
    }


    public String registerGCM() {

        gcm = GoogleCloudMessaging.getInstance(this);
        regId = getRegistrationId(context);

        if (TextUtils.isEmpty(regId)) {
            registerInBackground();
            Log.d("RegisterActivity",
                    "registerGCM - successfully registered with GCM server - regId: "
                            + regId);
        } else {
            try {
                installation.put("GCMSenderId", regId.isEmpty() ? getRegistrationId(context) : regId);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            Toast.makeText(getApplicationContext(),
//                    "RegId already available. RegId: " + regId,
//                    Toast.LENGTH_LONG).show();
        }
        return regId;
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
            return "";
        }
        return registrationId;
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regId = gcm.register(Config.GOOGLE_PROJECT_ID);
                    Log.d("RegisterActivity", "registerInBackground - regId: "
                            + regId);
                    msg = "Device registered, registration ID=" + regId;

                    storeRegistrationId(context, regId);
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.d("RegisterActivity", "Error: " + msg);
                }
                Log.d("RegisterActivity", "AsyncTask completed: " + msg);
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
//                Toast.makeText(getApplicationContext(),
//                        "Registered with Server.", Toast.LENGTH_LONG)
//                        .show();
                installation.put("GCMSenderId", msg.isEmpty() ? getRegistrationId(context) : msg);
            }
        }.execute(null, null, null);
    }

    private void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.commit();
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

    public void notificationSound() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
