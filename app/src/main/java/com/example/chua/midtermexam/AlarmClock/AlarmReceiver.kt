package com.example.chua.midtermexam.AlarmClock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Chua on 2/12/2018.
 */
class AlarmReceiver: BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {

        var getResult: String = intent!!.getStringExtra("extra")


        var service_intent: Intent =  Intent(context, RingtoneService::class.java)
        service_intent.putExtra("extra", getResult)
        context!!.startService(service_intent)
    }
}