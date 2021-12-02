package com.example.realestatemanager.ui.property

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.realestatemanager.R
import com.example.realestatemanager.database.DummyContent
import com.example.realestatemanager.database.DummyContent.DummyItem

class PropertyListRecyclerViewAdapter(
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

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        override fun toString(): String {
            return super.toString() + " '" + "mContentView.text" + " '"
        }
    }

}