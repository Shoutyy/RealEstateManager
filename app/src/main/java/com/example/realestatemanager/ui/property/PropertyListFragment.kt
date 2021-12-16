package com.example.realestatemanager.ui.property

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.realestatemanager.R
import java.lang.RuntimeException
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.Fragment
import com.example.realestatemanager.di.ListInjection


class PropertyListFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null

    private val propertyListViewModel : PropertyListViewModel by lazy { ViewModelProviders.of(this, ListInjection.provideViewModelFactory(requireContext()))[PropertyListViewModel::class.java] }
    private val propertyListAdapter: PropertyListRecyclerViewAdapter = PropertyListRecyclerViewAdapter(/*listener*/)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_property_list, container, false)

        //set adapter
        (view as RecyclerView).apply {
            view.layoutManager = LinearLayoutManager(context)
            adapter = propertyListAdapter
        }
        getProperties()
        return view
    }

    private fun getProperties() {
        propertyListViewModel.properties.observe(viewLifecycleOwner, Observer { propertyListAdapter.receiveData(it, listener) })
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
        fun onListFragmentInteraction(propertyId: Int)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PropertyListFragment().apply {}
    }
}