package com.example.realestatemanager.ui.property

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.R
import android.widget.ImageView
import android.widget.TextView
import com.example.realestatemanager.model.PropertyModelProcessed
import com.example.realestatemanager.model.IllustrationModelProcessed
import kotlinx.android.synthetic.main.fragment_property.view.*
import com.example.realestatemanager.util.Utils


class PropertyListRecyclerViewAdapter : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>() {

    private val properties = mutableListOf<PropertyModelProcessed>()
    private var mListener: PropertyListFragment.OnListFragmentInteractionListener? = null
    private val propertiesPhotos = mutableListOf<IllustrationModelProcessed>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(R.drawable.ic_launcher_background)
        val property: PropertyModelProcessed = properties[position]

        with(holder) {
            type.text = property.type
            district.text = property.district
            price.text = property.price
            mView.setOnClickListener { mListener?.onListFragmentInteraction(property.propertyId) }
        }

        if(propertiesPhotos.isNotEmpty()) {
            for (propertyPhoto in propertiesPhotos) {
                if (propertyPhoto.propertyId == property.propertyId) {
                    holder.img.setImageBitmap(Utils.getInternalBitmap(property.path, propertyPhoto.photoName, context))
                    break
                }
            }
        }
    }

    override fun getItemCount(): Int = properties.size

    fun receivePropertiesDataAndListener(properties: List<PropertyModelProcessed>, listener: PropertyListFragment.OnListFragmentInteractionListener?) {
        this.properties.clear()
        this.properties.addAll(properties)
        mListener = listener
        notifyDataSetChanged()
    }

    fun receivePropertiesPhotos(propertiesPhotos: List<IllustrationModelProcessed>, context: Context) {
        this.propertiesPhotos.clear()
        this.propertiesPhotos.addAll(propertiesPhotos)
        this.context = context
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val district: TextView = mView.property_district
        val price: TextView = mView.property_price
    }
}