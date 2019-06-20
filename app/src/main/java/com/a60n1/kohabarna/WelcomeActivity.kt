package com.a60n1.kohabarna

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
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    private val prefs: SharedPreferences = this.getSharedPreferences("com.a60n1.kohabarna", Context.MODE_PRIVATE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        if (!prefs.contains("1stTime")) {
            fadeItems(pillsIMG, txtWelcome, mainLayout)
            btnTandC.setOnClickListener {
                if (chxAccepted.isChecked) {
                    prefs.edit().putBoolean("1stTime", false).apply()
                    startActivity(Intent(this, IntroActivity::class.java))
                    finish()
                    //Toast.makeText(this@MainActivity,prefs.getBoolean("firstTime",true).toString(),Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@WelcomeActivity, getString(R.string.not_accepted), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            pillsIMG.visibility = View.INVISIBLE
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }
    }

    private fun fadeItems(img: ImageView, txt: TextView, lin: LinearLayout) {
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.interpolator = AccelerateInterpolator()
        fadeOut.duration = 1000
        fadeOut.setAnimationListener(object : AnimationListener {
            override fun onAnimationEnd(animation: Animation) {
                img.visibility = View.GONE
                txt.visibility = View.VISIBLE
                lin.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation) {}
            override fun onAnimationStart(animation: Animation) {}
        })
        img.startAnimation(fadeOut)
    }
}
