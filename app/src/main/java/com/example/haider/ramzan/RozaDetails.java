package com.example.haider.ramzan;

/**
 * Created by Haider on 3/22/2017.
 */

public class RozaDetails {

        // Roza Details Class for Getting Roza Attributes from Database and save in it than use it in array and bind listview
        // This Class Usage is in WebServiceRoza

    private String rozaid;
    private String sehar_time;
    private String iftar_time;

    public String getRozaid() {
        return rozaid;
    }

    public void setRozaid(String rozaid) {
        this.rozaid = rozaid;
    }

    public String getSehar_time() {
        return sehar_time;
    }

    public void setSehar_time(String sehar_time) {
        this.sehar_time = sehar_time;
    }

    public String getIftar_time() {
        return iftar_time;
    }

    public void setIftar_time(String iftar_time) {
        this.iftar_time = iftar_time;
    }

    public String ConcatDetails(){
        String details="";
        details +="Roza "+getRozaid()+"\tSehar "+getSehar_time()+"\tIftar "+getIftar_time();
        return details;
    }
}
