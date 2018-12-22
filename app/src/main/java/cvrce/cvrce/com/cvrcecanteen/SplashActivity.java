package cvrce.cvrce.com.cvrcecanteen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    final static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LayoutInflater layoutInflater;
        ViewGroup root;
        final View view = (CoordinatorLayout)findViewById(R.id.splash);


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if(!isOnline()){
                    Snackbar snackbar = Snackbar.make(view, "No Internet Connection",
                            Snackbar.LENGTH_INDEFINITE).setAction("Retry",
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    recreate();
                                }
                            });

                    snackbar.show();
                }
                else{

                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                finish();
            }
        }, SPLASH_TIME_OUT);






    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
