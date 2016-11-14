package com.example.vipulsublaniya.contactapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class sendMessagePage extends AppCompatActivity {
    Button send;
    EditText msg;
    TextView txt;
    SendSms snd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message_page);

        send = (Button) findViewById(R.id.send);
        msg = (EditText)findViewById(R.id.message);
        txt = (TextView)findViewById(R.id.textView);

        final String name = getIntent().getStringExtra("name");
        final String phoneNo = getIntent().getStringExtra("phoneNo");
        txt.setText("Send To: "+ phoneNo);
        send.setOnClickListener(new View.OnClickListener() {
            //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                //String OTP = Integer.toString(ThreadLocalRandom.current().nextInt(100011, 1000000));
                //this requires min api 21 i.e. lollipop

                //use random class instead
                Random rand = new Random();
                String OTP = Integer.toString(rand.nextInt(1000000 - 100000) + 100000);

                String message = "Hi,\nYour OTP is: " + OTP + "\nMessage: " + msg.getText().toString();


                snd = new SendSms(sendMessagePage.this);
                snd.sendSMSMessage(message, phoneNo);


                try {
                    //Calendar c = Calendar.getInstance();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                    String currentDateandTime = sdf.format(new Date());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name", name);
                    jsonObject.put("OTP", OTP);
                    jsonObject.put("Date", currentDateandTime);

                    new JSONCaching(sendMessagePage.this).writeAppendToJSONFile(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });



    }

    @Override
    protected void onStop() {
        //unregister the broadcastreceiver of sendSms class if there is any
        try{
            unregisterReceiver(snd.broadcastReceiver);
        }catch (Exception e){
            Log.e("broadcastreceiver not registered", "exception: receiver not registered");
        }

        super.onStop();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_message_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
