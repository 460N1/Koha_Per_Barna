package com.a60n1.kohabarna.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.a60n1.kohabarna.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val prefs: SharedPreferences = this.getSharedPreferences(packageName, Context.MODE_PRIVATE)
        //^marrim qasje ne settings
        txtWelcome.visibility = View.INVISIBLE
        mainLayout.visibility = View.INVISIBLE
        //^aplikacioni fillon me tekst te padukshem
        if (!prefs.contains("hera1")) {
            //^nese ne settings eshte definuar hera1, nuk eshte me hera e pare qe hapet app
            fadeItems(pillsIMG, txtWelcome, mainLayout)
            //^fshehe imazhin dhe shfaqe tekstin
            btnTandC.setOnClickListener {
                if (chxAccepted.isChecked) {
                    //^kontroll a jane pranuar termat dhe konditat
                    prefs.edit().putBoolean("hera1", false).apply()
                    //^vendoset hera1 ne settings
                    prefs.edit().putBoolean("siguria", swiSiguria.isChecked).apply()
                    prefs.edit().putBoolean("notify", swiNotifications.isChecked).apply()
                    //^vendosen ne settings ashtu siq jane zgjedhur
                    if (swiSiguria.isChecked) startActivity(Intent(this, IntroActivity::class.java))
                    //^nese siguria eshte ON, kalohet ne autentifikim
                    else startActivity(Intent(this, MainActivity::class.java))
                    //^perndryshe direkt ne aplikacion
                    finish()
                } else {
                    Toast.makeText(this@WelcomeActivity, getString(R.string.not_accepted), Toast.LENGTH_SHORT).show()
                    //^mesazh nese nuk pranohen kushtet
                }
            }
        } else {
            //^nese nuk eshte hera e pare
            pillsIMG.visibility = View.INVISIBLE
            //^fsheh imazhin
            if (prefs.getBoolean("siguria", true)) startActivity(Intent(this, IntroActivity::class.java))
            //^nese siguria ne settings eshte ON, hapet autentifikimi
            else startActivity(Intent(this, MainActivity::class.java))
            //^nese eshte OFF, hapet app direkt
            finish()
        }
    }

    private fun fadeItems(img: ImageView, txt: TextView, lin: LinearLayout) {
        val fadeOut = AlphaAnimation(1f, 0f)
        //^definon llojin e animacionit
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = 1000
        //^percaktojme gjatesine e animacionit (milisekonda) 1000ms=1sek
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.GONE
                //^fotoja zhduket
                txt.visibility = View.VISIBLE
                lin.visibility = View.VISIBLE
                //^teksti shfaqet
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
            //^nevojshem te definohen funksionet
        })
        img.startAnimation(fadeOut)
        //^fillo animacionin
    }
}