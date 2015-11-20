package com.pemikir.flappy2048;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    WebView webview;
    InterstitialAd adverd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        AdView mAdView = (AdView) findViewById(R.id.adView);

        if (Utils.isConnectingToInternet(getApplicationContext())) {
            adverd = Utils.newInterstitialAd(getApplicationContext());
            AdRequest adRequest = new AdRequest.Builder().setRequestAgent("android_studio:ad_template").build();
            adverd.loadAd(adRequest);
            mAdView.loadAd(new AdRequest.Builder().build());
        }

        webview = (WebView) findViewById(R.id.webview);
        webview.loadUrl("file:///android_asset/Flapp-2048/index.html");
//        vistaWeb.setWebChromeClient(new MyCustomChromeClient(this));
//        vistaWeb.setWebViewClient(new MyCustomWebViewClient(this));
        webview.getSettings().setPluginState(WebSettings.PluginState.ON);
        webview.clearCache(true);
        webview.clearHistory();
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adverd.isLoaded()) {
            adverd.show();
        }
    }
}
