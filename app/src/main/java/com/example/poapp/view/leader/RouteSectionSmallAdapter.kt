package com.example.poapp.view.leader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.ConfirmRouteViewModel

class RouteSectionSmallAdapter(
    private val values: List<RouteSection>,
    private val mViewModel: ConfirmRouteViewModel,
    private val onSectionClickedListener: OnSectionClickedListener
) :
    RecyclerView.Adapter<RouteSectionSmallAdapter.RouteSectionSmallItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteSectionSmallItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_route_section_small, parent, false)
        return RouteSectionSmallItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteSectionSmallItemHolder, position: Int) {
        val item = values[position]
        holder.start.text = mViewModel.getSectionStartName(item)
        holder.end.text = mViewModel.getSectionEndName(item)
        holder.time.text = item.czasPrzejscia.toString()

        holder.itemView.setOnClickListener {
            onSectionClickedListener.onClick(item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class RouteSectionSmallItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val start: TextView = iv.findViewById(R.id.start_value_small)
        val end: TextView = iv.findViewById(R.id.end_value_small)
        val time: TextView = iv.findViewById(R.id.time_value_small)
    }

}