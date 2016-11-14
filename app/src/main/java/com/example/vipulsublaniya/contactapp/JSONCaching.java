package com.example.vipulsublaniya.contactapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by ViPul Sublaniya on 14-11-2016.
 */
public class JSONCaching extends AppCompatActivity {
    public static String cacherFileName = "sentMessageText.txt";

    Context context;
    public JSONCaching(Context context) {
        this.context = context;
    }

    public JSONArray readJSONFromFile() {
        JSONArray jarray = new JSONArray();
        String data;
        try {
            InputStream is = context.openFileInput(cacherFileName);
            if ( is != null ) {
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String receivedString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receivedString = br.readLine()) != null ) {
                    stringBuilder.append(receivedString);
                }

                is.close();
                data = stringBuilder.toString();

                Log.d("readData", data);
                jarray = new JSONArray(data);
                //return jarray;
            }else{
                Log.e("Error", "No data or file found");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jarray;
    }

    public void writeAppendToJSONFile(JSONObject jo)
    {
        JSONArray jarray = readJSONFromFile();
        jarray.put(jo);
        String data = jarray.toString();
        try {
            OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(cacherFileName, Context.MODE_PRIVATE));
            osw.write(data);
            osw.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        Log.e("cacher file content after write",data);


    }
}
