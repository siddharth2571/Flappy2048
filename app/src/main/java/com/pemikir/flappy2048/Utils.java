package com.pemikir.flappy2048;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by siddharth on 10/4/2015.
 */
public class Utils {

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr != null) {
            NetworkInfo[] info = conMgr.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void InterstitialAdLodingandShow(Context ctx) {
        final InterstitialAd interstitialAd = new InterstitialAd(ctx);
        interstitialAd.setAdUnitId(ctx.getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.

            }
        });
    }


    public static InterstitialAd newInterstitialAd(Context ctx) {
        InterstitialAd interstitialAd = new InterstitialAd(ctx);
        interstitialAd.setAdUnitId(ctx.getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.

            }
        });
        return interstitialAd;
    }




}
