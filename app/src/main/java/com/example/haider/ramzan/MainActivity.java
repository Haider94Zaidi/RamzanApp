package com.example.haider.ramzan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

        // Application's Entry Point

    public static final String PREF_NAME = "RamzanApp_Settings";
    public static final String firstrun = "firstrun";
    public static final String city = "city";
    public static final String firqah = "firqah";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Intent that will start the HomeScreen if Not First Time User Entry otherwise go to FirstSettings Class. */
                SharedPreferences preferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);

                String t = preferences.getString(city,"");
                String s = preferences.getString(firqah,"");
//                new WebServiceRoza(getApplicationContext()).execute(t,s);
                String c = preferences.getString(firstrun, "");
//                WebServiceRoza wsr = new WebServiceRoza(getApplicationContext());
//                wsr.doInBackground(t,s);
                if (c.equals("no")) {
                    Intent mainIntent = new Intent(MainActivity.this, HomeScreen.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
                else{
                    Intent mainIntent = new Intent(MainActivity.this, FirstSettings.class);
                    MainActivity.this.startActivity(mainIntent);
                    MainActivity.this.finish();
                }
            }
        }, 2000);

    }
}
