package com.example.realestatemanager.ui

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.example.realestatemanager.R
import android.util.Log
import android.widget.Toast
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import android.content.Intent
import com.example.realestatemanager.ui.map.PICK_PROPERTY_DATA
import com.example.realestatemanager.ui.form.FormBaseActivity
import com.example.realestatemanager.ui.property.PropertyDetailActivity
import com.example.realestatemanager.ui.property.PropertyDetailFragment
import com.example.realestatemanager.ui.property.PropertyListFragment
import com.example.realestatemanager.ui.emptyPropertyDetail.EmptyPropertyDetailFragment
import com.example.realestatemanager.ui.form.AddFormActivity
import com.example.realestatemanager.ui.form.UpdateFormActivity
import com.example.realestatemanager.ui.map.MapActivity

const val INTENT_MAIN_TO_DETAIL = "INTENT_MAIN_TO_DETAIL"
const val INTENT_MAIN_TO_UPDATE = "INTENT_MAIN_TO_UPDATE"
const val PICK_PROPERTY_REQUEST = 1234

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

    //inflate the appropriate menu and add it to the Toolbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (activity_property_detail_container != null) {
            menuInflater.inflate(R.menu.main_menu_toolbar_tablet, menu)
        } else {
            menuInflater.inflate(R.menu.main_menu_toolbar_phone, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.search_button -> { Log.e("Search","do something"); true }
            R.id.update_button -> {
                if (fragmentPropertyDetail != null) {
                    val intent = Intent(this, UpdateFormActivity::class.java)
                    intent.putExtra(INTENT_MAIN_TO_UPDATE, propertyId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.update_button_select_property_first, Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.add_button -> {
                val intent = Intent(this, AddFormActivity::class.java)
                intent.putExtra(INTENT_MAIN_TO_UPDATE, propertyId)
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
                R.id.activity_home_drawer_map -> {
                    val intent = Intent(this, MapActivity::class.java)
                    //startActivity(intent)*
                    startActivityForResult(intent, PICK_PROPERTY_REQUEST)
                    true
                }
                else -> false
            }
            this.activity_main_drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_PROPERTY_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    val propertyId: Int = data.getIntExtra(PICK_PROPERTY_DATA, 1)
                    onListFragmentInteraction(propertyId)
                }
            }
        }
    }

    //fragment
    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    private val fragmentPropertyList = PropertyListFragment.newInstance()
    private var fragmentPropertyDetail: PropertyDetailFragment? = null
    private var fragmentEmptyPropertyDetail: EmptyPropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
    private var propertyId: Int = 0

    private fun initAndAddFragment() {
        fragmentTransaction.add(R.id.activity_property_list_container, fragmentPropertyList)
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
            intent.putExtra(INTENT_MAIN_TO_DETAIL, propertyId)
            startActivity(intent)
        }   else if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail != null) {
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


