package com.a60n1.kohabarna.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.a60n1.kohabarna.R
import kotlinx.android.synthetic.main.activity_suggestions_questions.*

class SuggestionsQuestions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions_questions)
        btnQandS.setOnClickListener { sendEmail() }
        //^percakton veprimin kur te preket butoni
    }

    private fun sendEmail() {
        val to = arrayOf(getString(R.string.dev_support1))
        val cc = arrayOf(getString(R.string.dev_support2))
        //^definojme tekstualisht se ku do dergohet
        val emailIntent = Intent(Intent.ACTION_SEND)
        //^percaktojme llojin e intent
        emailIntent.data = Uri.parse("mailto:")
        //^percaktojme URI (mail)
        emailIntent.type = "text/plain"
        //^percaktojme permbajtjen e email
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_CC, cc)
        //^percaktohet se ku do dergohen nga definimi
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
        //^vendosim subject te definuar ne strings
        emailIntent.putExtra(Intent.EXTRA_TEXT, txtQandS.text)
        //^marrim tekstin e shkruar dhe e vendosim ne email
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            //^tento te dergohet email, ofrohet opsioni per te zgjedhur klientin
            finish()
            Toast.makeText(applicationContext, getString(R.string.toast_finmail), Toast.LENGTH_SHORT).show()
        } catch (ex: android.content.ActivityNotFoundException) {
            //^rasti kur nuk ka klient per email
            Toast.makeText(
                applicationContext,
                getString(R.string.toast_noclient), Toast.LENGTH_SHORT
            ).show()
        }

    }
}
