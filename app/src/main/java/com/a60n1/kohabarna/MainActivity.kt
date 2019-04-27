package com.a60n1.kohabarna

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuBottom.visibility = View.INVISIBLE
        fab.setOnClickListener {
            menuBottom.visibility =
                if (menuBottom.visibility == View.VISIBLE) View.INVISIBLE else View.VISIBLE
        }
        fabAdd.setOnClickListener {
            Toast.makeText(this@MainActivity, "Add was pressed", Toast.LENGTH_SHORT).show()
        }
        fabRem.setOnClickListener {
            Toast.makeText(this@MainActivity, "Reminder was pressed", Toast.LENGTH_SHORT).show()
        }
        fabCal.setOnClickListener {
            Toast.makeText(this@MainActivity, "Calendar was pressed", Toast.LENGTH_SHORT).show()
        }
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
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
            R.id.nav_home -> {
            }
            R.id.nav_tools -> {
            }
            R.id.nav_share -> {
            }
            R.id.nav_send -> {
            }
            R.id.nav_help -> {
            }
            R.id.nav_about -> {
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}