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
        //^thirrim autentifikimin dhe kalojme kontrollen e aplikacionit tek autentifikuesi
    }

    override fun onResume() {
        //^nese behet kalimi nga nje app ne tjeter dhe kthehet tek app jone
        super.onResume()
        //^vazhdohet ekzekutimi kudo qe ka mbet
        fingerprintHelper.startAuth()
        //^rifillohet autentifikimi
    }

    override fun onPause() {
        super.onPause()
        //^nese behet kalimi nga nje app ne tjeter, pauzohet ekzekutimi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            fingerprintHelper.stopAuth()
        //^nese perkrahet, autentifkuesi ndal punen e vet
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintHardwareFound() {
        //^ne rast se nuk ka senzor per fingerprint, hapet direkt pamja kryesore
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        finish()
        //^mbylle IntroActivity, parandalon kthimin te autentifikuesi duke prekur 'back'
    }

    override fun onAuthFailed(errorCode: Int, errorMessage: String?) {
        toast(getString(R.string.auth_failed))
        //^rasti kur autentifikimi deshton
    }

    @SuppressLint("SetTextI18n")
    override fun onNoFingerPrintRegistered() {
        //^rasti kur nuk ka fingerprint te regjistruar
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        //^kalon direkt ne MainActivity
        finish()
    }

    @SuppressLint("SetTextI18n")
    override fun onBelowMarshmallow() {
        //^nese eshte versioni me i ulte se Marshmallow
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        //^autentifikuesi nuk do funksiononte, kalon direkt ne MainActivity
        finish()
    }

    override fun onAuthSuccess(cryptoObject: FingerprintManager.CryptoObject?) {
        //^identtifikimi me sukses
        toast(getString(R.string.auth_success))
        startActivity(applicationContext, Intent(applicationContext, MainActivity::class.java), null)
        //^kalon ne MainActivity
        finish()
    }
}
