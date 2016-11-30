package com.mantambakberas.ikamantab.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.helper.SessionManager;

public class SplashActivity extends AppCompatActivity {

    SessionManager session;
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new SessionManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


        if(session.isLoggedIn()) {
            Intent ii = new Intent(SplashActivity.this, MainActivity.class);
            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            SplashActivity.this.startActivity(ii);
            SplashActivity.this.finish();
        }


    }
}
