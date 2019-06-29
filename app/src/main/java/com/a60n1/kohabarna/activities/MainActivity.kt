package com.a60n1.kohabarna.activities

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.widget.Toast
import com.a60n1.kohabarna.R
import com.a60n1.kohabarna.db.Adapter
import com.a60n1.kohabarna.db.Barna
import com.a60n1.kohabarna.db.SQLHelper
import com.google.android.gms.appinvite.AppInviteInvitation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var lists: ArrayList<Barna>
    private lateinit var db: SQLHelper
    private lateinit var data: Cursor
    //^definojme llojin e variabla qe do perdorim me vone
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        //^percaktojme se qka do hapet kur te bahet swipe ne te djathte
        fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, ShtoNdrysho::class.java))
            finish()
        }
        //^kur preket + te hapet ShtoNdrysho
        lists = ArrayList()
        //^definojme arraylist te re
        db = SQLHelper(applicationContext)
        //^lidhemi me databaze
        data = db.dataGet
        //^marrim te dhenat nga databaza
        showData()
        //^shfaqim te dhenat
        rvList.layoutManager = GridLayoutManager(applicationContext, 1)
        //^percaktojme layout te recyclerView si Grid me nga nje objekt ne rresht
        rvList.adapter = Adapter(applicationContext, lists)
        //^percaktojme adapterin qe duhet perdorur dhe qka do perdor si burim te info
    }

    override fun onBackPressed() {
        //^kur te preket back
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            //^nese drawer open dhe preket back, mbyllet drawer
        } else {
            //nese drawer nuk eshte hapun
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                finishAndRemoveTask()
            //^mbylle aplikacionin komplet nese perkrahet
            else
                super.onBackPressed()
            //^perndryshe vetem lejo back normal
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //^kur te preket nje nga items ne drawer
        when (item.itemId) {
            R.id.nav_tools -> {
                //^kur preket opsionet
                startActivity(Intent(this@MainActivity, Opsionet::class.java))
            }
            R.id.nav_playstore -> {
                //^kur preket playstore
                val appPackageName = packageName // getPackageName() from Context or Activity object
                //^marrim emrin e aplikacionit
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                    //^hapim app ne play store
                } catch (anfe: android.content.ActivityNotFoundException) {
                    //^nese per qfaredo arsye nuk ka apo nuk funksionon play store
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                    //^hapet play store ne browser
                }
            }
            R.id.nav_help -> {
                //^preket help
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.github_link))
                    )
                )
                //^hapet github
            }
            R.id.nav_share -> {
                //^preket share
                val intent = AppInviteInvitation.IntentBuilder(getString(R.string.app_name_nav))
                    //^caktohet titulli i share
                    .setMessage(getString(R.string.share_message))
                    //^caktohet mesazh i share
                    .setDeepLink(Uri.parse(getString(R.string.play_link)))
                    //^caktohet URI e aplikacionit qe do dergohet
                    .setCallToActionText(getString(R.string.share_c2a))
                    //^sdi
                    .build()
                //^kompleto
                startActivityForResult(intent, 101)
                //^hape share
            }
            R.id.nav_email -> {
                //^preket email
                startActivity(Intent(this@MainActivity, SuggestionsQuestions::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        //^mbylle drawer kurdo dhe cilido opsion te zgjidhet (qe te pyti kur ekzekutohet, si te preket najnjana)
        return true
    }

    private fun showData() {
        if (data.count == 0)
        //^nese nuk ka arrit me marr te dhena apo nuk ka
            Toast.makeText(
                applicationContext,
                getString(R.string.fail),
                Toast.LENGTH_SHORT
            ).show()
        while (data.moveToNext())
        //^perndryshe nese ka...
            lists.add(
                Barna(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5)
                )
            )
        //^shto ne list te dhenat nga qdo barne, nje nga nje
    }

    override fun onStart() {
        super.onStart()
        showData()
    }
}