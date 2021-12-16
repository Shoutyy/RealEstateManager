package com.example.realestatemanager.ui.property

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.R
import android.widget.ImageView
import android.widget.TextView
import com.example.realestatemanager.model.ModelsProcessedPropertyList
import kotlinx.android.synthetic.main.fragment_property.view.*



class PropertyListRecyclerViewAdapter(
    /*private val mListener: PropertyListFragment.OnListFragmentInteractionListener?*/)
    : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>(){

    private val modelsProcessed = mutableListOf<ModelsProcessedPropertyList>()
    private var mListener: PropertyListFragment.OnListFragmentInteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(R.drawable.ic_launcher_background)
        val model: ModelsProcessedPropertyList = modelsProcessed[position]

        with(holder) {
            //img.setImageBitmap(property.images[0])
            type.text = model.type
            district.text = model.district
            price.text = model.price
            mView.setOnClickListener { mListener?.onListFragmentInteraction(model.propertyId) }
        }
    }

    override fun getItemCount(): Int = modelsProcessed.size

    fun receiveData(modelsProcessed: List<ModelsProcessedPropertyList>, listener: PropertyListFragment.OnListFragmentInteractionListener?) {
        this.modelsProcessed.clear()
        this.modelsProcessed.addAll(modelsProcessed)
        mListener = listener
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val district: TextView = mView.property_district
        val price: TextView = mView.property_price
    }
}