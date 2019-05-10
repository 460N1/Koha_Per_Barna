package com.a60n1.kohabarna

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_shto_ndrysho.*

class ShtoNdrysho : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shto_ndrysho)
        val db = SQLHelper(applicationContext)
        try {
            if (intent.getStringExtra("id").isNotEmpty()) {
                etEmri.setText(intent.getStringExtra("emri"))
                etPershkrimi.setText(intent.getStringExtra("pershkrimi"))
                etDataF.setText(intent.getStringExtra("dataF"))
                etDataM.setText(intent.getStringExtra("dataM"))
                etDoktori.setText(intent.getStringExtra("doktori"))
                btnAdd.text = getString(R.string.btnNdrysho)
                btnAdd.setOnClickListener {
                    val emriText = etEmri.text.toString().trim()
                    val pershkrimiText = etPershkrimi.text.toString().trim()
                    val dataFText = etDataF.text.toString().trim()
                    val dataMText = etDataM.text.toString().trim()
                    val doktoriText = etDoktori.text.toString().trim()
                    db.deleteData(intent.getStringExtra("id"))
                    db.addData(
                        emriText,
                        pershkrimiText,
                        dataFText,
                        dataMText,
                        doktoriText,
                        "0"
                    )
                    Toast.makeText(
                        this@ShtoNdrysho,
                        getString(R.string.toastNdryshim),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    startActivity(Intent(this@ShtoNdrysho, MainActivity::class.java))
                }
            }
        } catch (e: Exception) {
            btnAdd.setOnClickListener {
                val emriText = etEmri.text.toString().trim()
                val pershkrimiText = etPershkrimi.text.toString().trim()
                val dataFText = etDataF.text.toString().trim()
                val dataMText = etDataM.text.toString().trim()
                val doktoriText = etDoktori.text.toString().trim()
                db.addData(
                    emriText,
                    pershkrimiText,
                    dataFText,
                    dataMText,
                    doktoriText,
                    "0"
                )
                Toast.makeText(
                    this@ShtoNdrysho,
                    getString(R.string.toastNjoftim),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
                startActivity(Intent(this@ShtoNdrysho, MainActivity::class.java))
            }
        }
    }
}
