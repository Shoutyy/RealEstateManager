package com.example.realestatemanager.ui.property

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.R
import kotlinx.android.synthetic.main.property_detail_fragment.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.realestatemanager.di.DetailInjection
import com.example.realestatemanager.model.ModelsProcessedPropertyDetail

private const val ARG_PROPERTY_ID = "ARG_PROPERTY_ID"

class PropertyDetailFragment : Fragment() {

    companion object {
        fun newInstance(propertyId: Int) =
            PropertyDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PROPERTY_ID, propertyId)
                }
            }
    }

    private var propertyId: Int = 0
    private val propertyDetailViewModel: PropertyDetailViewModel by lazy { ViewModelProviders.of(this, DetailInjection.provideViewModelFactory(requireContext())).get(PropertyDetailViewModel::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            propertyId = it.getInt(ARG_PROPERTY_ID)
        }
    }

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
        propertyDetailViewModel.getProperty(propertyId).observe(viewLifecycleOwner, Observer { updateUi(it) })
    }

    private fun updateUi(model: ModelsProcessedPropertyDetail) {
        with(model) {
            property_detail_description.text = description
            property_detail_surface.text = surface
            property_detail_rooms.text = rooms
            property_detail_bathrooms.text = bathrooms
            property_detail_bedrooms.text = bedrooms
            property_detail_path.text = path
            complement?.let {
                property_detail_complement.visibility = View.VISIBLE
                property_detail_complement.text = complement
            }
            property_detail_city.text = city
            property_detail_postal_code.text = postalCode
            property_detail_country.text = country
        }
    }
}