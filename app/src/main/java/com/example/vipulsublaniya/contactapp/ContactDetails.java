package com.example.vipulsublaniya.contactapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

//class for Contact Details
public class ContactDetails extends AppCompatActivity {
    TextView contactno, name;
    Button sendMessageButton;
    String nameText;
    JSONObject obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);


        name = (TextView) findViewById(R.id.name);
        contactno = (TextView) findViewById(R.id.contact);
        sendMessageButton = (Button)findViewById(R.id.sendMessageButton);

        //get intent i.e. contact data
        String contact = getIntent().getStringExtra("contact");
        try {
            obj = (JSONObject) new JSONTokener(contact).nextValue();
            nameText = obj.getString("firstName")+" "+obj.getString("lastName");


            name.setText("Name : "+ nameText);
            contactno.setText("Contact No. : "+ obj.getString("mobileno"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContactDetails.this, sendMessagePage.class);
                try {
                    i.putExtra("name",nameText);
                    i.putExtra("phoneNo",obj.getString("mobileno"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {

        //reload main activity to reload Older Message Data
        Intent i = new Intent(ContactDetails.this, MainActivity.class);
        //finish();
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_details, menu);
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
