package com.example.haider.ramzan;

import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import static android.R.id.list;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Haider on 3/22/2017.
 */

public class WebServiceRoza extends AsyncTask<String,Void,String> {
    private Context context;

    public WebServiceRoza(Context ctx){
        this.context = ctx;
    }
    @Override
    protected String doInBackground(String... params) {
        this.context = context;
        String s = GetRozas(params);                // RozaList function Call

        return s;
    }

    private String GetRozas(String[] s){            // get the RozaList with respect to Sect from php WebService
        String ret="";
        String Url = "http://10.1.1.19/RamzanAppWebService.php";
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(Url);

            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("tablename", s[0]));
            list.add(new BasicNameValuePair("firqa",s[1]));

            httppost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpresponse = httpclient.execute(httppost);
            HttpEntity entity = httpresponse.getEntity();
            ret = readResponse(httpresponse);

        }
        catch (Exception ex){
            ret = ex.getMessage().toString();
            System.out.print(ret);
        }
        return ret;
    }

    public String readResponse(HttpResponse res) throws IOException{       // Get the Json Response from Server and saving it to File
        InputStream is=null;
        String filesaved="";
        String return_text="";
        try {
            is=res.getEntity().getContent();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
            String line="";
            StringBuffer sb=new StringBuffer();
            while ((line=bufferedReader.readLine())!=null)
            {
                sb.append(line);
            }

               filesaved = FileWriting(sb.toString());                      // Function Call to write File
//            return_text=sb.toString();
        } catch (Exception e)
        {
            return_text = e.getMessage().toString();
        }

        return filesaved;

    }

    private String FileWriting(String JsonString) throws IOException {          // Write the Response to File
        String ok = "";
        try{

            FileOutputStream fileout = context.openFileOutput("RamzanAppRozaList.txt", MODE_PRIVATE);
            System.out.print(this.context);
            OutputStreamWriter outputWriter=new OutputStreamWriter(fileout);
            outputWriter.write(JsonString);
            outputWriter.close();
//            ok = "File Written";
            File f = new File("RamzanAppRozaList.txt");                         // File Saved at Runtime with Json Array
            f.getAbsolutePath().toString();
            ok = f.getAbsolutePath().toString();
        }
        catch(Exception ex){
            ok = ex.getMessage().toString();
        }
        return ok;
    }
    public String[] FileReading() throws IOException{                           // Reads Json Array from File
        String value = "";

        String[] arr = new String[29];
        FileInputStream fin = context.openFileInput("RamzanAppRozaList.txt");
        int c;

        while( (c = fin.read()) != -1){
            value = value + Character.toString((char)c);
        }

        fin.close();

        JSONObject jsonResponse;
        try {
            JSONArray jArr = new JSONArray(value);
            int length = jArr.length();
            for (int i=0;i<length;i++){
//                JSONObject jsonObject = (JSONObject) jArr.get(i);
                JSONArray jArr2 = (JSONArray) jArr.get(i);
                RozaDetails rd = new RozaDetails();                     // Roza Class Containing Roza Details populate at runtime
                rd.setRozaid((String) jArr2.getString(0));              // Roza Class method
                rd.setSehar_time((String) jArr2.getString(1));          // Roza Class method
                rd.setIftar_time((String) jArr2.getString(2));          // Roza Class method
                arr[i] = rd.ConcatDetails();                            // Roza Class method, structures the chunks into detail and save it to String array as a single array element

            }


        } catch (JSONException e) {
             e.getMessage().toString();
        }
        return arr;                                                     // Return array of Roza Details which than binds with the listview
    }
}
