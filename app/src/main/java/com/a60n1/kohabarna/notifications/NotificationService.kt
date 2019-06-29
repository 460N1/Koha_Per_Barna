package com.a60n1.kohabarna.notifications

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import com.a60n1.kohabarna.R
import com.a60n1.kohabarna.activities.MainActivity
import java.util.*

@Suppress("DEPRECATION")
class NotificationService : IntentService("NotificationService") {
    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000
    @SuppressLint("NewApi")
    private fun createChannel() {//PER ANDROID >= O // aplikon channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            //^krijon nje menagjer te notifikacioneve
            val importance = NotificationManager.IMPORTANCE_HIGH
            //^percakton rendesine e notifikacioneve si high
            val notificationChannel = NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, importance
                //definon nje kanal per notifikacione
            )
            notificationChannel.enableVibration(true)
            //^vibrimi on
            notificationChannel.setShowBadge(true)
            //^shfaqe badge
            notificationChannel.enableLights(true)
            //^lejo LED te bej drite
            notificationChannel.lightColor = Color.parseColor(getString(R.string.primary_dark))
            //^percakto ngjyren e LED // ngjyra primary e aplikacionit
            notificationChannel.description = getString(R.string.notification_channel_description)
            //^teksti i notifikacionit
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            //^percakton se a do shfaqet teksti nga lockscreen
            notificationManager.createNotificationChannel(notificationChannel)
            //^krijo kanalin siq u definua deri tani
        }
    }

    companion object {
        const val CHANNEL_ID = "com.a60n1.kohabarna.CHANNEL_ID"
        //id e kanalit
        const val CHANNEL_NAME = "Lajmrimet"
        //emri i kanalit
    }

    override fun onHandleIntent(intent: Intent?) {//PERNDRYSHE NESE ANDROID < O
        createChannel()
        //krijo kanalin
        var timestamp: Long = 0
        if (intent != null && intent.extras != null)
            timestamp = intent.extras!!.getLong("timestamp")
        //^nese intenti eshte ne rregull dhe jo null, merre kohen
        if (timestamp > 0) {
            val context = this.applicationContext
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, MainActivity::class.java)
            //^definojme intent me qellim te kalimi ne MainActivity
            val title = getString(R.string.app_name_nav)
            //^titulli i notifikacionit
            val message =
                getString(R.string.notification_text)
            //^teksti i not.
            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)
            //^te dhena shtese per notifikacion
            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            //^trego qe ka gjasa te hapet nga jasht aplikacioni
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp
            //^qasja ne objekt te kalendarit qe te mund te kuptohet formati i kohes
            val pendingIntent =
                PendingIntent.getActivity(context, (0..999).random(), notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            //^si ma heret
            val res = this.resources
            //^prej nga me i marr resurset si imazhi i app ne notifikacion
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            //^ben za nese e ka te lshume
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotification = Notification.Builder(
                    this,
                    CHANNEL_ID
                )
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_stat_name) //ikona e vogel si zile
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher)) //ikona e app
                    .setAutoCancel(true) //te mund te swipe e te hjeket
                    .setContentTitle(title) //titulli
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message)
                    ) //vendoset qfar stili do kete
                    .setContentText(message)
                    .build()
                //^krijohet si definuar deri tash
            } else {
                mNotification = Notification.Builder(this)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_stat_name)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                    .setAutoCancel(true)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle(title)
                    .setStyle(
                        Notification.BigTextStyle()
                            .bigText(message)
                    )
                    .setSound(uri)
                    .setContentText(message)
                    .build()
                //^same
            }
            notificationManager.notify(mNotificationId, mNotification)
            //^krijo notifikacionin kur t'arrin
        }
    }
}