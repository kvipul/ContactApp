package com.example.vipulsublaniya.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ViPul Sublaniya on 13-11-2016.
 */
public class F1 extends android.support.v4.app.Fragment {
    View fragmentView;
    TextView txt;
    ListView contactListView;

    public F1(){

    }

    //@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the layout
        fragmentView=  inflater.inflate(R.layout.frag_f1, container, false);
        contactListView = (ListView) fragmentView.findViewById(R.id.listView);


        //fetch json string from json file in assets folder and list them using arrayadapter
        String jsonData;
        try {
            InputStream is = getActivity().getAssets().open("fakeContacts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonData = new String(buffer, "UTF-8");

            JSONObject obj = (JSONObject) new JSONTokener(jsonData).nextValue();
            final JSONArray fakeContacts = obj.getJSONArray("contacts");

            Log.e("jsondata", fakeContacts.toString());
            Log.e("jsondata", fakeContacts.getJSONObject(1).toString());

                                                    /*ArrayList<HashMap<String,String> > adapterList = new ArrayList<HashMap<String,String> >();
                                                    HashMap<String, String > hmap;

                                                    for (int i=0 ; i<fakeContacts.length();i++){
                                                        JSONObject jo = fakeContacts.getJSONObject(i);

                                                        String id = jo.getString("id");
                                                        String name = jo.getString("name");
                                                        String mobileno = jo.getString("mobileno");

                                                        //now put value into hashmap
                                                        hmap = new HashMap<String, String >();
                                                        hmap.put("id",id);
                                                        hmap.put("name", name);
                                                        hmap.put("mobileno", mobileno);

                                                        //add value to array list
                                                        adapterList.add(hmap);
                                                    }*/

            List<String> adapterList = new ArrayList<String>();
            for (int i=0 ; i<fakeContacts.length();i++){
                JSONObject jo = fakeContacts.getJSONObject(i);
                adapterList.add(jo.getString("firstName")+" "+jo.getString("lastName"));
            }
            final ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, adapterList);

            //set adapter to list view
            contactListView.setAdapter(adapter);

            //set onClickListener to each list item
            contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = (String) parent.getItemAtPosition(position);
                    Toast.makeText(getContext(), item+" Selected", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(getContext(), ContactDetails.class);
                    try {
                        i.putExtra("contact", fakeContacts.getJSONObject(position).toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    startActivity(i);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }

        //return fragment view
        return fragmentView;
    }
}
