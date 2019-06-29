package com.a60n1.kohabarna.notifications

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.*

class NotificationUtils {
    fun setNotification(timeInMilliSeconds: Long, activity: Activity) {
        if (timeInMilliSeconds > 0) {
            //^notifikacione tentohen te krijohen vetem nese nuk ka konflikt ne kohen e percaktuar
            //^psh me parandalu nese dataF eshte 10/07 e data e mbarimit gabimisht 05/07
            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE) as AlarmManager
            //^definojme nje sherbim te llojit alarm
            val alarmIntent =
                Intent(activity.applicationContext, AlarmReceiver::class.java)
            //^definojme nje intent per klasen AlarmReceiver
            alarmIntent.putExtra("reason", "notification")
            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
            //^vendosim te dhenat shtese si koha
            val calendar = Calendar.getInstance()
            //^definojme nje instance te kalendarit ne menyre qe te kuptoj sistemi kohen e percaktuar
            calendar.timeInMillis = timeInMilliSeconds
            val pendingIntent =
                PendingIntent.getBroadcast(activity, Random().nextInt(), alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
            //^krijo nje intent prites qe do pret per ndonje thirrje te jashtme (pret notifikacione)
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            //^filloj krejt keto qe u definun deri tash
        }
    }
}
