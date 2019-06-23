package com.a60n1.kohabarna

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
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.appinvite.AppInviteInvitation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var lists: ArrayList<Barna>
    private lateinit var db: SQLHelper
    private lateinit var data: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuBottom.visibility = View.INVISIBLE
        fab.setOnClickListener {
            menuBottom.visibility =
                if (menuBottom.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
        fabRem.setOnClickListener {
            Toast.makeText(this@MainActivity, "Reminder was pressed", Toast.LENGTH_SHORT).show()
            //TODO: MUTE NOTIFICATION FUNCTION
        }
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
        fabAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, ShtoNdrysho::class.java))
        }
        lists = ArrayList()
        db = SQLHelper(applicationContext)
        data = db.dataGet
        showData()
        rvList.layoutManager = GridLayoutManager(applicationContext, 1)
        rvList.adapter = Adapter(applicationContext, lists)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                finishAndRemoveTask()
            else
                super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_tools -> {
                //TODO:CREATE ACTIVITY FOR OPTIONS
            }
            R.id.nav_playstore -> {
                val appPackageName = packageName // getPackageName() from Context or Activity object
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
                } catch (anfe: android.content.ActivityNotFoundException) {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                        )
                    )
                }
            }
            R.id.nav_help -> {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/460N1/Koha_Per_Barna/blob/master/README.md")
                    )
                )
            }
            R.id.nav_share -> {
                val intent = AppInviteInvitation.IntentBuilder("Koha per Barna")
                    .setMessage("Shikojeni aplikacionin Koha per Barna! https://play.google.com/store/apps/com.a60n1.kohabarna")
                    .setDeepLink(Uri.parse("https://play.google.com/store/apps/com.a60n1.kohabarna"))
                    .setCallToActionText("Get it now!")
                    .build()
                startActivityForResult(intent, 101)
            }
            R.id.nav_email -> {
                startActivity(Intent(this@MainActivity, SuggestionsQuestions::class.java))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showData() {
        if (data.count == 0)
            Toast.makeText(
                applicationContext,
                getString(R.string.fail),
                Toast.LENGTH_SHORT
            ).show()
        while (data.moveToNext())
            lists.add(
                Barna(
                    data.getString(0),
                    data.getString(1),
                    data.getString(2),
                    data.getString(3),
                    data.getString(4),
                    data.getString(5),
                    data.getString(6)
                )
            )
    }

    override fun onStart() {
        super.onStart()
        showData()
    }
}