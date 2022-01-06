package com.example.realestatemanager.ui.result

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.realestatemanager.R
import com.example.realestatemanager.ui.emptyPropertyDetail.EmptyPropertyDetailFragment
import com.example.realestatemanager.ui.searchForm.INTENT_SEARCH_TO_RESULT
import com.example.realestatemanager.ui.property.PropertyDetailActivity
import com.example.realestatemanager.ui.property.PropertyDetailFragment
import com.example.realestatemanager.ui.property.PropertyListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

const val INTENT_RESULT_TO_DETAIL = "INTENT_RESULT_TO_DETAIL"

class ResultActivity : AppCompatActivity(), PropertyListFragment.OnListFragmentInteractionListener {

    private val propertiesId = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureToolbar()
        retrievesIntent()
        initAndAddFragment()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_SEARCH_TO_RESULT)) {
            intent.getIntArrayExtra(INTENT_SEARCH_TO_RESULT)
                ?.let { this.propertiesId.addAll(it.toMutableList()) }
        }
    }

    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    private var fragmentPropertyDetail: PropertyDetailFragment? = null
    private var fragmentEmptyPropertyDetail: EmptyPropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
    private var propertyId: Int = 0

    private fun initAndAddFragment() {
        fragmentTransaction.add(R.id.activity_property_list_container, PropertyListFragment.newInstance(propertiesId.toIntArray()))
        if (containerPropertyDetail == null && activity_property_detail_container != null) {
            fragmentEmptyPropertyDetail = EmptyPropertyDetailFragment.newInstance()
            fragmentTransaction.add(R.id.activity_property_detail_container, fragmentEmptyPropertyDetail!!)
        }
        fragmentTransaction.commit()
    }

    override fun onListFragmentInteraction(propertyId: Int) {
        this.propertyId = propertyId
        if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail == null) {
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(INTENT_RESULT_TO_DETAIL, propertyId)
            startActivity(intent)
        } else if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail != null) {
            addFragment(propertyId, fragmentEmptyPropertyDetail!!)
        } else {
            addFragment(propertyId, fragmentPropertyDetail!!)
        }
    }

    private fun addFragment(propertyId: Int, removeThisFragment: Fragment) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(removeThisFragment)
        fragmentPropertyDetail = PropertyDetailFragment.newInstance(propertyId)
        fragmentTransaction.add(R.id.activity_property_detail_container, fragmentPropertyDetail!!)
        fragmentTransaction.commit()
    }

}