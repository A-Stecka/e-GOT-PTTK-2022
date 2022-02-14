package com.example.poapp.view.leader

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.Route
import com.example.poapp.view.tourist.route.OnRouteClickedListener
import com.example.poapp.viewModel.ConfirmRouteViewModel

class RoutesToConfirmAdapter(
    private val values: List<Route>,
    private val mViewModel: ConfirmRouteViewModel,
    private val onRouteClickedListener: OnRouteClickedListener
) :
    RecyclerView.Adapter<RoutesToConfirmAdapter.RouteItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.route_for_leader_item, parent, false)
        return RouteItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteItemHolder, position: Int) {
        val item = values[position]
        holder.tourist.text = mViewModel.getTouristName(item.id.toLong())
        holder.date.text = item.dataPrzejscia
        holder.points.text = item.punkty.toString()
        holder.start.text = mViewModel.getStartNameForRoute(item.id)
        holder.end.text = mViewModel.getEndNameForRoute(item.id)
        holder.group.text = mViewModel.getMountainGroupName(item.id.toLong())

        holder.itemView.setOnClickListener { onRouteClickedListener.onItemClick(item) }
    }

    override fun getItemCount(): Int = values.size

    inner class RouteItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val tourist: TextView = iv.findViewById(R.id.item_tourist_name)
        val date: TextView = iv.findViewById(R.id.item_route_date)
        val points: TextView = iv.findViewById(R.id.item_confirm_route_points_value)
        val start: TextView = iv.findViewById(R.id.item_confirm_route_start_value)
        val end: TextView = iv.findViewById(R.id.item_confirm_route_end_value)
        val group: TextView = iv.findViewById(R.id.item_confirm_route_mountain_group_value)
    }

}