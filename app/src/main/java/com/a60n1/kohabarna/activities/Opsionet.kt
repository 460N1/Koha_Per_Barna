package com.a60n1.kohabarna.activities

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.a60n1.kohabarna.R
import com.a60n1.kohabarna.db.SQLHelper
import kotlinx.android.synthetic.main.activity_opsionet.*
import kotlin.system.exitProcess

class Opsionet : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opsionet)
        val prefs = this@Opsionet.getSharedPreferences(packageName, Context.MODE_PRIVATE)
        if (prefs.getBoolean("siguria", true))
            swiOptSig.isChecked = true
        if (prefs.getBoolean("notify", true))
            swiOptNot.isChecked = true
        deleteAll.setOnClickListener {
            val db = SQLHelper(applicationContext)
            for (i in 0..50)
                db.deleteData(i.toString())
            exitProcess(0)
        }
        reset.setOnClickListener {
            prefs.edit().clear().apply()
            deleteAll.performClick()
            exitProcess(0)
        }
    }
}
