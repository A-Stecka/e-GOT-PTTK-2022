package com.example.poapp.view.tourist.route

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.Route
import com.example.poapp.viewModel.RouteViewModel

class RouteAdapter(
    private val context: Context,
    private val values: List<Route>,
    private val mViewModel: RouteViewModel,
    private val onRouteClickedListener: OnRouteClickedListener
) :
    RecyclerView.Adapter<RouteAdapter.RouteItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_route_item, parent, false)
        return RouteItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteItemHolder, position: Int) {
        val item = values[position]
        holder.date.text = item.dataPrzejscia
        holder.points.text = item.punkty.toString()
        holder.start.text = mViewModel.getStartNameForRoute(item.id)
        holder.end.text = mViewModel.getEndNameForRoute(item.id)
        holder.status.text = item.status

        when (holder.status.text) {
            context.resources.getString(R.string.confirmed) -> holder.status.setTextColor(context.getColor(R.color.green))
            context.resources.getString(R.string.rejected) -> holder.status.setTextColor(context.getColor(R.color.red))
            else -> holder.status.setTextColor(context.getColor(R.color.black))
        }

        holder.itemView.setOnClickListener { onRouteClickedListener.onItemClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class RouteItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val date: TextView = iv.findViewById(R.id.route_date_value)
        val points: TextView = iv.findViewById(R.id.route_points_value)
        val start: TextView = iv.findViewById(R.id.route_start_value)
        val end: TextView = iv.findViewById(R.id.route_end_value)
        val status: TextView = iv.findViewById(R.id.route_status_value)
    }

}