package cvrce.cvrce.com.cvrcecanteen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class SplashActivity extends AppCompatActivity {

    long splashTime=200, time=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final RelativeLayout rl = findViewById(R.id.splash);

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                try{
                    while(time<splashTime){
                        time+=10;
                        //Log.d("debug tag", "here");
                    }
                }
                catch (Exception e){

                }
               finally{
                    if(!isOnline()){
                        Snackbar snackbar = Snackbar.make(rl, "No Internet Connection",
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
                }
            }
        };

        thread.start();

    }

    private boolean isOnline(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}
