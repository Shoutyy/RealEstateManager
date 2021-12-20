package com.example.realestatemanager.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.content.Intent
import kotlinx.android.synthetic.main.toolbar.*
import com.example.realestatemanager.ui.form.FormActivity
import com.example.realestatemanager.ui.property.PropertyDetailActivity
import com.example.realestatemanager.ui.property.PropertyDetailFragment
import com.example.realestatemanager.ui.property.PropertyListFragment
import com.example.realestatemanager.ui.emptyPropertyDetail.EmptyPropertyDetailFragment

const val INTENT_PROPERTY_ID = "INTENT_PROPERTY_ID"

class MainActivity : AppCompatActivity(), PropertyListFragment.OnListFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureToolbar()
        configureDrawerLayout()
        configureNavigationView()
        initAndAddFragment()
    }

    private fun configureToolbar() { setSupportActionBar(toolbar) }

    //inflate the menu and add it to the Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search_button -> { println("do something"); true }
            R.id.update_button -> { println("do something"); true }
            R.id.add_button -> {
                val intent = Intent(this, FormActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, activity_main_drawer_layout,
            toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_main_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView() {
        activity_home_nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.activity_main_drawer_temp -> { println("do something"); true }
                else -> false
            }
            this.activity_main_drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    //fragment
    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    private lateinit var fragmentPropertyList: PropertyListFragment
    private var fragmentPropertyDetail: PropertyDetailFragment? = null
    private var fragmentEmptyPropertyDetail: EmptyPropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = null

    private fun initAndAddFragment() {
        fragmentPropertyList = PropertyListFragment.newInstance()
        fragmentTransaction.add(R.id.activity_property_list_container, fragmentPropertyList)
        containerPropertyDetail = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
        if (containerPropertyDetail == null && activity_property_detail_container != null) {
            fragmentEmptyPropertyDetail = EmptyPropertyDetailFragment.newInstance()
            fragmentTransaction.add(R.id.activity_property_detail_container, fragmentEmptyPropertyDetail!!)
        }
        fragmentTransaction.commit()
    }

    override fun onListFragmentInteraction(propertyId: Int) {
        if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail == null) {
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(INTENT_PROPERTY_ID, propertyId)
            startActivity(intent)
        }   else if (fragmentEmptyPropertyDetail == null && fragmentEmptyPropertyDetail != null) {
            addFragment(propertyId, fragmentEmptyPropertyDetail!!)
        }   else {
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


