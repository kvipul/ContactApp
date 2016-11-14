package com.example.vipulsublaniya.contactapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;


public class SendSms {
    Context context;
    BroadcastReceiver broadcastReceiver;

    static  String smsStatus = "";
    public SendSms(Context context) {
        this.context = context;
    }

    protected void sendSMSMessage(String message, String phoneNo) {
        Log.i("Send SMS", "");

        try {

            String SENT = "SMS_SENT";


            PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);

            //BroadcastReceiver added to get the status of sent SMS
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int resultCode = getResultCode();
                    switch (resultCode) {
                        case Activity.RESULT_OK:
                            Toast.makeText(context, "SMS Sent", Toast.LENGTH_LONG).show();

                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Toast.makeText(context, "Generic failure", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Toast.makeText(context, "No service", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Toast.makeText(context, "Null PDU", Toast.LENGTH_LONG).show();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Toast.makeText(context, "Radio off", Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };
            context.registerReceiver(broadcastReceiver, new IntentFilter(SENT));

            //send message to phoneNo
            SmsManager smsMgr = SmsManager.getDefault();
            smsMgr.sendTextMessage(phoneNo, null, message, sentPI, null);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage() + "!\n" + "Failed to send SMS", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

}

