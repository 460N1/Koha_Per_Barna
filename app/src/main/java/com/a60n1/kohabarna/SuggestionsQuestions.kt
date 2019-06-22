package com.a60n1.kohabarna

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_suggestions_questions.*

class SuggestionsQuestions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestions_questions)
        btnQandS.setOnClickListener { sendEmail() }
    }

    private fun sendEmail() {
        val to = arrayOf("edona.haziraj@gmail.com")
        val cc = arrayOf("agon.hoxha@uni-pr.edu")
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to)
        emailIntent.putExtra(Intent.EXTRA_CC, cc)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Koha per Barna / Q&S")
        emailIntent.putExtra(Intent.EXTRA_TEXT, txtQandS.text)

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."))
            finish()
            Toast.makeText(applicationContext, "Finished sending email...", Toast.LENGTH_SHORT).show()
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "There is no email client installed.", Toast.LENGTH_SHORT
            ).show()
        }

    }
}
