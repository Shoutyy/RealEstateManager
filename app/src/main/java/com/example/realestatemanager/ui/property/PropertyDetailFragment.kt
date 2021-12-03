package com.example.realestatemanager.ui.property

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.realestatemanager.R

class PropertyDetailFragment : Fragment() {

    companion object {
        fun newInstance() = PropertyDetailFragment()
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
    }
}