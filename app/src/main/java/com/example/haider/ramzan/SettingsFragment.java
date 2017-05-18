package com.example.haider.ramzan;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.text1;

public class SettingsFragment extends Fragment{

    TextView cityText,selectcity;
    ListView cities_list;
    Button save_btn;
    RadioGroup firqaselect;
    String[] cities;
    public static final String PREF_NAME = "RamzanApp_Settings";    // SharedPreference Name
    public static final String city = "city";
    public static final String firqah = "firqah";
    public static final String firstrun = "firstrun";

    SharedPreferences sharedpreference;
    String selectedfirqah;


    public SettingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_settings, container, false);
        cities = getResources().getStringArray(R.array.cities);
        cities_list  = (ListView) rootview.findViewById(R.id.listView_cities);
        cityText = (TextView) rootview.findViewById(R.id.savedcity_text);
        save_btn = (Button) rootview.findViewById(R.id.save_settings);
        selectcity = (TextView) rootview.findViewById(R.id.itemtext);
        firqaselect = (RadioGroup) rootview.findViewById(R.id.radioGroup);
        sharedpreference = getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        firqaselect.check(R.id.radio1);

        // Getting the User / App Settings from shared preferences

        SharedPreferences preferences = getContext().getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String c = preferences.getString(city,"");
        String f = preferences.getString(firqah,"");
        cityText.setText(c.toString());
        if(f.equals("Hanfiya")){
            firqaselect.check(R.id.radio1);
            selectedfirqah = "Hanfiya";

        }
        if(f.equals("Jafriya")){
            firqaselect.check(R.id.radio2);
            selectedfirqah = "Jafriya";
        }

        firqaselect.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {      // Checkboxes listender for selecting Sect

                switch(checkedId){
                    case R.id.radio1:
                        Toast.makeText(getContext(),"Hanfiya Selected", Toast.LENGTH_SHORT).show();
                        selectedfirqah = "Hanfiya";
                        break;
                    case R.id.radio2:
                        Toast.makeText(getContext(), "Jafriya Selected", Toast.LENGTH_SHORT).show();
                        selectedfirqah = "Jafriya";
                        break;
                }
            }
        });

        // Populating the Listview in Settings Fragment with Cities, the cities array is defined in Strings.xml

        cities = getResources().getStringArray(R.array.cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,text1,cities);
        cities_list.setAdapter(adapter);

            cities_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String text = (String) parent.getItemAtPosition(position);
                    selectcity.setText("Selected City");
                    cityText.setText(text);
                }
            });


            save_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {       // The Button Listener save the App and User Settings
                    if (!cityText.getText().toString().equals("*Your City*")) {
                        SharedPreferences.Editor editor = sharedpreference.edit();

                        editor.putString(city, cityText.getText().toString());
                        editor.putString(firqah, selectedfirqah.toString());
                        editor.putString(firstrun, "no");
                        editor.commit();
//                        String s =  new WebServiceRoza(getActivity().getApplicationContext()).doInBackground(cityText.getText().toString(),selectedfirqah.toString()).toString();
                        try {
                            WebServiceRoza wsr = new WebServiceRoza(getContext());
                            String s = wsr.execute(cityText.getText().toString(), selectedfirqah.toString()).toString();
                            Toast.makeText(getContext(), "Saving Settings", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {
                            Alert(ex.getMessage().toString());
                        }
                    }
                    else{
                        Toast.makeText(getContext(), "You Must Select a City", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // AlertBox shows when the user switch to Settings Fragment

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Select your City from A List");
            AlertDialog al = builder.create();
            al.show();



            return rootview;


    }

    public void Alert(String h){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(h);
        AlertDialog al = builder.create();
        al.show();
    }



}
