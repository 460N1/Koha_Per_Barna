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
    }

    private fun sendEmail() {
        val to = arrayOf(getString(R.string.dev_support1))
        val cc = arrayOf(getString(R.string.dev_support2))
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_CC, cc)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mail_subject))
        emailIntent.putExtra(Intent.EXTRA_TEXT, txtQandS.text)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
            Toast.makeText(applicationContext, getString(R.string.toast_finmail), Toast.LENGTH_SHORT).show()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                getString(R.string.toast_noclient), Toast.LENGTH_SHORT
            ).show()
        }

    }
}
