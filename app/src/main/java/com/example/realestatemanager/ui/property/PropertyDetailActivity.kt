package com.example.realestatemanager.ui.property

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.realestatemanager.ui.INTENT_MAIN_TO_DETAIL
import com.example.realestatemanager.ui.result.INTENT_RESULT_TO_DETAIL
import com.example.realestatemanager.ui.form.UpdateFormActivity


const val INTENT_DETAIL_TO_UPDATE = "INTENT_DETAIL_TO_UPDATE"

class PropertyDetailActivity : AppCompatActivity() {

    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        retrievesIntent()
        configureToolbar()
        initAndAddFragment()
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_MAIN_TO_DETAIL)) {
            propertyId = intent.getIntExtra(INTENT_MAIN_TO_DETAIL, 0)
        } else if (intent.hasExtra(INTENT_RESULT_TO_DETAIL)) {
            propertyId = intent.getIntExtra(INTENT_RESULT_TO_DETAIL, 0)
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu_toolbar_phone, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.update_button -> {
                if (propertyId != 0) {
                    val intent = Intent(this, UpdateFormActivity::class.java)
                    intent.putExtra(INTENT_DETAIL_TO_UPDATE, propertyId)
                    startActivity(intent)
                } else {
                    //TODO: STRING
                    Toast.makeText(this, "Something wrong append !", Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance(propertyId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }
}