@file:Suppress("DEPRECATION")

package com.a60n1.kohabarna.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import com.a60n1.kohabarna.R
import com.multidots.fingerprintauth.FingerPrintAuthCallback
import com.multidots.fingerprintauth.FingerPrintAuthHelper
import org.jetbrains.anko.toast

class IntroActivity : AppCompatActivity(), FingerPrintAuthCallback {
    private lateinit var fingerprintHelper: FingerPrintAuthHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        fingerprintHelper = FingerPrintAuthHelper.getHelper(this, this)
    }

    override fun onResume() {
        super.onResume()
        fingerprintHelper.startAuth()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            fingerprintHelper.stopAuth()
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintHardwareFound() {
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        finish()
    }

    override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
        toast(getString(R.string.auth_failed))
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintRegistered() {
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
    }

    @SuppressLint("SetTextI18n")
    override fun onBelowMarshmallow() {
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
    }

    override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
        toast(getString(R.string.auth_success))
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        finish()
    }
}
