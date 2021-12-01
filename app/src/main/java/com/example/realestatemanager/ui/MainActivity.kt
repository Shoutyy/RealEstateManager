package com.example.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.configureToolbar()
    }

    private fun configureToolbar() = setSupportActionBar(toolbar)

    //inflate the menu and add it to the Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search_button -> { println("do something"); true }
            R.id.create_button -> { println("do something"); true }
            R.id.add_button -> { println("do something"); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}