package com.example.realestatemanager.ui.property

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.R
import com.example.realestatemanager.ui.property.PropertyListFragment.OnListFragmentInteractionListener
import com.example.realestatemanager.database.DummyContent.DummyItem
import android.widget.ImageView
import android.widget.TextView
import com.example.realestatemanager.model.Address
import com.example.realestatemanager.model.Property
import kotlinx.android.synthetic.main.fragment_property.view.*


class PropertyListRecyclerViewAdapter(
    private val properties: MutableList<Property>?,
    private val addresses: MutableList<Address>?,
    private val mValues: List<DummyItem>,
    private val mListener: PropertyListFragment.OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>(){

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener= View.OnClickListener { v ->
            val item = v.tag as DummyItem
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.img.setImageResource(R.drawable.ic_launcher_background)
        holder.type.text = "type"
        holder.location.text = "location"
        holder.price.text = "price"

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val location: TextView = mView.property_location
        val price: TextView = mView.property_price

        override fun toString(): String {
            return super.toString() + " '" + "mContentView.text" + " '"
        }
    }

}