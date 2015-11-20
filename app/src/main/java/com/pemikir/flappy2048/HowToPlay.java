package com.pemikir.flappy2048;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class HowToPlay extends AppCompatActivity {

    InterstitialAd adverd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_play);
        if (Utils.isConnectingToInternet(getApplicationContext())) {
            try {

//                AdRequest adRequest = new AdRequest.Builder()
//                        .setRequestAgent("android_studio:ad_template").build();
//                Config.interstitialAd.loadAd(new AdRequest.Builder().build());
                adverd = new InterstitialAd(HowToPlay.this);
                adverd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                adverd.loadAd(new AdRequest.Builder().build());
                adverd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        adverd.show();
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {

                    }

                    @Override
                    public void onAdClosed() {
                        // Proceed to the next level.
                        adverd = new InterstitialAd(HowToPlay.this);
                        adverd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (adverd.isLoaded()) {
                adverd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
