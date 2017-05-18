package com.example.haider.ramzan;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;



public class AhadeesFragment extends Fragment implements View.OnClickListener{

    // Ahadees Fragment

    TextView hadees;
    Button btn_hadees;
    String[] hadees_arr;
    int count = 0;
    public AhadeesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ahadees, container, false);

        hadees_arr = getResources().getStringArray(R.array.hadeesarray);        // The hadeesarray is defined in Strings.xml

        hadees = (TextView) view.findViewById(R.id.hadees_textview);            // Textview which shows the particular hadees
        btn_hadees = (Button) view.findViewById(R.id.hadees_btn);

        btn_hadees.setOnClickListener(this);        // this is the call for OnClick(View V) at line 44
        return view;

    }

    @Override
    public void onClick(View v) {
        if(count < hadees_arr.length){
            hadees.setText(hadees_arr[count].toString());       // render the hadees in String array into hadees textview
            count++;
        }
        else{
            count = 0;                                          // for repeating ahadees
        }

    }


}
