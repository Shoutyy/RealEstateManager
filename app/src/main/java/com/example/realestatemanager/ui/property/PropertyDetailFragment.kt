package com.example.realestatemanager.ui.property

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.property_detail_fragment.*

class PropertyDetailFragment : Fragment() {

    companion object {
        private const val ARG_PROPERTY_ID = "ARG_PROPERTY_ID"

        fun newInstance(propertyId: Int?): PropertyDetailFragment {
            val fragment = PropertyDetailFragment()
           propertyId?.let {
               val args = Bundle()
               args.putInt(ARG_PROPERTY_ID, propertyId)
               fragment.arguments = args
           }
            return fragment
        }
    }

    private var propertyId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            propertyId = it.getInt(ARG_PROPERTY_ID)
        }
    }

    private lateinit var viewModel: PropertyDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    /* //deprecated
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider.of(this).get(PropertyDetailViewModel::class.java)
    }
     */

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PropertyDetailViewModel::class.java]
        this.updateUi()
    }

    private fun updateUi() {
        /*with(property) {
            property_detail_description.text = description
        }
        //property_detail_description.text = property.description
        property_detail_surface.text = property.surface
        property_detail_rooms.text = property.rooms.toString()
        property_detail_bathrooms.text = property.bathrooms.toString()
        property_detail_bedrooms.text = property.bedrooms.toString()*/
    }
}