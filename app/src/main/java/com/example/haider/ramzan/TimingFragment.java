package com.example.haider.ramzan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.id.text1;



public class TimingFragment extends Fragment {


    ListView rozalist;
    String[] rozaarray;
    Context thiscontext;
    int count=0;
    public static final String PREF_NAME = "RamzanApp_Settings";    // SharedPreference Name
    public static final String city = "city";
    public static final String firqah = "firqah";
    public static final String firstrun = "firstrun";
    public TimingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview =  inflater.inflate(R.layout.fragment_timing, container, false);
        rozalist = (ListView) rootview.findViewById(R.id.timinglist);
        thiscontext = container.getContext();
        WebServiceRoza wsr = new WebServiceRoza(getContext());      // WebserviceRoza Object
        try {

            rozaarray = wsr.FileReading();              // Reading the File to populate Listview
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,text1,rozaarray);
            rozalist.setAdapter(adapter);
        } catch (IOException e) {
//            WifiManager wifi = (WifiManager) thiscontext.getSystemService(Context.WIFI_SERVICE);
//            if(wifi.isWifiEnabled()){
//                SharedPreferences preferences = getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
//                String c = preferences.getString(city,"");
//                String f = preferences.getString(firqah,"");
//                String[] arr = {c,f};
//                new WebServiceRoza(getContext()).doInBackground(c.toString(),f.toString());
//                Alert(s);
//                try {
//                    rozaarray = wsr.FileReading();              // Reading the File to populate Listview
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,text1,rozaarray);
//                rozalist.setAdapter(adapter);
//            }
//            else{
//                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//            }
            Alert(e.getMessage().toString());
        }

//        WebServiceRoza wsr = new WebServiceRoza(getContext());
//        String s = wsr.execute("testtable","Hanfiya").toString() ;
//
//        Alert(s);
        return rootview;
    }

    public void Alert(String s){            // Custom Method for Showing Messages
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(s);
        AlertDialog al = builder.create();
        al.show();
    }

}
