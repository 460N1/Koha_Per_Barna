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
        //^marrim qasje ne settings te app
        if (prefs.getBoolean("siguria", true))
            swiOptSig.isChecked = true
        //^vendosum switch Sigura ashtu siq jane ne settings
        if (prefs.getBoolean("notify", true))
            swiOptNot.isChecked = true
        //^vendosum switch Notifikacione ashtu siq jane ne settings
        deleteAll.setOnClickListener {
            val db = SQLHelper(applicationContext)
            //^marrim qasje ne databaze
            for (i in 0..50)
                db.deleteData(i.toString())
            //^fshijme barnat me id prej 0 deri 50
            //bazuar ne supozim se nuk do jene me shume
            //se 50 barna te regjistruara njekohesisht
            exitProcess(0)
            //^mbyllet aplikacioni
        }
        reset.setOnClickListener {
            prefs.edit().clear().apply()
            //^fshijme settings
            deleteAll.performClick()
            //^detyrojme fshirjen e te dhenave (siq definuar ma larte)
            exitProcess(0)
            //^mbyllet aplikacioni
        }
        swiOptSig.setOnClickListener {
            prefs.edit().putBoolean("siguria", swiOptSig.isChecked).apply()
            //^vendosum ne settings se ne cilen pozit eshte switch Siguria
        }
        swiOptNot.setOnClickListener {
            prefs.edit().putBoolean("notify", swiOptNot.isChecked).apply()
            //^vendosum ne settings se ne cilen pozit eshte switch Notifikacione
        }
    }
}
