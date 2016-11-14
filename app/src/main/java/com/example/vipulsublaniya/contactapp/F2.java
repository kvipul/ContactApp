package com.example.vipulsublaniya.contactapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ViPul Sublaniya on 13-11-2016.
 */
public class F2 extends android.support.v4.app.Fragment {
    View fragmentView;
    ListView msgList;


    public F2(){

    }

    //To refresh the data of Message list
    public void refresh(){
        //JSONCaching smp = new JSONCaching();

        //fetch cached data and list them into listView
        JSONArray olderMessage = new JSONCaching(getActivity()).readJSONFromFile();
        Log.e("older message", olderMessage.toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");

        List<String> adapterList = new ArrayList<String>();
        for (int i=olderMessage.length()-1 ; i>=0;i--){
            JSONObject jo = null;

            try {
                jo = olderMessage.getJSONObject(i);
                Date date = dateFormat.parse(jo.getString("Date"));

                String nameText = jo.getString("name").toUpperCase();
//                SpannableString ss = new SpannableString(nameText);
//                int textSizeBig = getResources().getDimensionPixelSize(R.dimen.text_size_big);
//                ss.setSpan(new AbsoluteSizeSpan(textSizeBig),0,nameText.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                String otpText = jo.getString("OTP");
                String dateText = new SimpleDateFormat("dd MMM,yyyy hh:mm:ss").format(date);

                adapterList.add(nameText + "\nOTP:" + otpText + "    "+dateText);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, adapterList);

        msgList.setAdapter(adapter);
    }

    //    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentView = inflater.inflate(R.layout.frag_f2, container, false);
        msgList = (ListView) fragmentView.findViewById(R.id.messageList);

        //load data initially
        refresh();
        return fragmentView;
    }
}
