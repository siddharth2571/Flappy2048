package com.pemikir.flappy2048;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class FeedBack extends AppCompatActivity {

    Button feedback;
    public static final String APPLICATION_ID = "XQCMGBUN3JV6sJQHUg4w7jDylbo8bMDJNYwjmkpn";
    public static final String CLIENT_KEY = "l2j4QGvKep1QRBz6TBTr1ubQQ2odKdtpadxD46HR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_feed_back);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        feedback= (Button) findViewById(R.id.feedback);




        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }




}
