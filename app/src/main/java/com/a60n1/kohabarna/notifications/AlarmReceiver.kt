package com.a60n1.kohabarna.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val service = Intent(context, NotificationService::class.java)
        //^krijojme nje intent
        service.putExtra("reason", intent.getStringExtra("reason"))
        service.putExtra("timestamp", intent.getLongExtra("timestamp", 0))
        //^vendosim te dhena shtese mbi ate intent
        service.data = Uri.parse("custom://" + System.currentTimeMillis())
        //^percaktojme se kur do thirret ky intent (kur te gjendet nje URI ne sistem qe i pershtatet formatit)
        context.startService(service)
        //^fillojme NotificationService si sherbim (jo aktivitet)
    }
}