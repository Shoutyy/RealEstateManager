package com.example.realestatemanager.ui.property

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*
import com.example.realestatemanager.ui.INTENT_PROPERTY_ID

class PropertyDetailActivity : AppCompatActivity() {

    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        this.retrieveIntent()
        this.configureToolbar()
        this.initAndAddFragment()
    }

    private fun retrieveIntent() {
        if (intent.hasExtra(INTENT_PROPERTY_ID)) {
            propertyId = intent.getIntExtra(INTENT_PROPERTY_ID, 0)
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance(propertyId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }
}