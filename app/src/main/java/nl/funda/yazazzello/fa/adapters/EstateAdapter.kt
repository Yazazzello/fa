package nl.funda.yazazzello.fa.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_estate_view.view.*
import nl.funda.yazazzello.fa.R
import nl.funda.yazazzello.fa.datamodels.EstateAgency
import java.util.*

/**
 * Created by yazazzello on 2/28/17.
 */
class EstateAdapter : RecyclerView.Adapter<EstateAdapter.ViewHolder>() {

    var list: MutableList<MutableMap.MutableEntry<EstateAgency, Int>> = ArrayList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return list[position].key.agencyId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_estate_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.apply {
            val item = list[position].key
            with(this.itemView) {
                item_agency_id.text = "${item.agencyId}"
                item_agency_name.text = item.agencyName
                item_counter.text = "estates: ${list[position].value}"
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}