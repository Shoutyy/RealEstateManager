package com.example.realestatemanager.ui.property

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.realestatemanager.model.Address
import com.example.realestatemanager.model.Property
import com.example.realestatemanager.R
import com.example.realestatemanager.database.DummyContent
import com.example.realestatemanager.database.DummyContent.DummyItem
import java.lang.RuntimeException
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.realestatemanager.di.ListInjection


class PropertyListFragment : Fragment() {

    //private var columnCount = 1
    private var listener: OnListFragmentInteractionListener? = null

    private var propertyListViewModel : PropertyListViewModel? = null
    private var properties: MutableList<Property> = mutableListOf()
    private var addresses: MutableList<Address> = mutableListOf()
    private var adapter: PropertyListRecyclerViewAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_property_list, container, false)


        //set adapter
        if (view is RecyclerView) {
            view.layoutManager = LinearLayoutManager(context)
            this.adapter = PropertyListRecyclerViewAdapter(properties, addresses, DummyContent.ITEMS, listener)
            view.adapter = this.adapter
        }
        this.configureViewModel()
        this.getCurrentProperty()
        this.getCurrentAddress()
        return view
    }

    private fun configureViewModel() {
        val mViewModelFactory = ListInjection.provideViewModelFactory(requireContext())
        this.propertyListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PropertyListViewModel::class.java)
        this.propertyListViewModel!!.init()
    }

    private fun getCurrentProperty() {
        this.propertyListViewModel!!.getProperties().observe(viewLifecycleOwner, Observer {  updateDataProperties(it) })
    }

    private fun updateDataProperties(properties: List<Property>) {
        this.properties.clear()
        this.properties.addAll(properties)
        this.notifyRecyclerView()
    }

    private fun getCurrentAddress() {
        this.propertyListViewModel!!.getAddresses().observe(viewLifecycleOwner, Observer { updateDataAddresses(it) })
    }

    private fun updateDataAddresses(addresses: List<Address>) {
        this.addresses.clear()
        this.addresses.addAll(addresses)
        this.notifyRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyRecyclerView() {
        if (properties.isNotEmpty()&& addresses.isNotEmpty()) {
            this.adapter!!.notifyDataSetChanged()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener{
        fun onListFragmentInteraction(item: DummyItem?)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PropertyListFragment().apply {}
    }
}