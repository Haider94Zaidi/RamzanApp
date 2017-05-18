package com.example.haider.ramzan;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.id.text1;

public class FirstSettings extends AppCompatActivity {


    /* The Activity Calls when the user has first interaction with the application and confgiure its settings accordingly
    *  Shared Preferences are used for Application Settings*/


    TextView cityText,selectcity;
    ListView cities_list;
    Button save_btn;
    RadioGroup firqaselect;
    String[] cities;
    public static final String PREF_NAME = "RamzanApp_Settings";
    public static final String city = "city";
    public static final String firqah = "firqah";
    public static final String firstrun = "firstrun";

    SharedPreferences sharedpreference;
    String selectedfirqah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_settings);


        cities = getResources().getStringArray(R.array.cities);
        cities_list  = (ListView) findViewById(R.id.listView_cities);
        cityText = (TextView) findViewById(R.id.savedcity_text);
        save_btn = (Button) findViewById(R.id.save_settings);
        selectcity = (TextView) findViewById(R.id.itemtext);
        firqaselect = (RadioGroup) findViewById(R.id.radioGroup);
        sharedpreference = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        firqaselect.check(R.id.radio1);
        SharedPreferences preferences = getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        String c = preferences.getString(city,"");
        String f = preferences.getString(firqah,"");
        cityText.setText(c.toString());
        selectedfirqah = f.toString();



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
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch(checkedId){
                    case R.id.radio1:
                        Toast.makeText(FirstSettings.this,"Hanfiya Selected", Toast.LENGTH_SHORT).show();
                        selectedfirqah = "Hanfiya";
                        break;
                    case R.id.radio2:
                        Toast.makeText(FirstSettings.this, "Jafriya Selected", Toast.LENGTH_SHORT).show();
                        selectedfirqah = "Jafriya";
                        break;
                }
            }
        });

        cities = getResources().getStringArray(R.array.cities);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FirstSettings.this,android.R.layout.simple_list_item_1,text1,cities);
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
            public void onClick(View v) {
                if (!cityText.getText().toString().equals("") && !selectedfirqah.toString().equals("")) {
                    SharedPreferences.Editor editor = sharedpreference.edit();

                    editor.putString(city, cityText.getText().toString());
                    editor.putString(firqah, selectedfirqah.toString());
                    editor.putString(firstrun,"no");
                    editor.commit();
                    WebServiceRoza wsr = new WebServiceRoza(FirstSettings.this);
                    String s = wsr.execute(cityText.getText().toString(), selectedfirqah.toString()).toString();
                    Toast.makeText(FirstSettings.this, "Saving Settings", Toast.LENGTH_SHORT).show();
                    Toast.makeText(FirstSettings.this, "Saved", Toast.LENGTH_SHORT).show();
                    Intent inte = new Intent(FirstSettings.this,MainActivity.class);
                    startActivity(inte);

                }
                else{
                    Toast.makeText(FirstSettings.this, "You Must Select a City and Sect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(FirstSettings.this);
        builder.setMessage("Select your City from A List");
        AlertDialog al = builder.create();
        al.show();



    }
}
