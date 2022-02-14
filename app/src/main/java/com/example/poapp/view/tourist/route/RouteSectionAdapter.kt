package com.example.poapp.view.tourist.route

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.RouteViewModel

class RouteSectionAdapter(
    private val context: Context,
    private val values: List<RouteSection>,
    private val mViewModel: RouteViewModel
) :
    RecyclerView.Adapter<RouteSectionAdapter.RouteSectionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteSectionItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_mountain_pass_item, parent, false)
        return RouteSectionItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouteSectionItemHolder, position: Int) {
        val item = values[position]
        holder.start.text = mViewModel.getStartPointName(item)
        holder.end.text = mViewModel.getEndPointName(item)
        holder.points.text = mViewModel.getRouteSectionPoints(item).toString()
        holder.through.text = mViewModel.getThroughPointName(item)
        holder.name.text = mViewModel.getRouteSectionName(item)

        holder.proofLabel.text = context.resources.getString(R.string.proof)
        if (mViewModel.hasProof(item)) {
            holder.proof.text = context.resources.getString(R.string.yes_capitalized)
            holder.proof.setTextColor(context.getColor(R.color.green))
        }
        else {
            holder.proof.text = context.resources.getString(R.string.no_capitalized)
            holder.proof.setTextColor(context.getColor(R.color.red))
        }
    }

    override fun getItemCount(): Int = values.size

    inner class RouteSectionItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val start: TextView = iv.findViewById(R.id.pass_start)
        val end: TextView = iv.findViewById(R.id.pass_end)
        val points: TextView = iv.findViewById(R.id.pass_points)
        val through: TextView = iv.findViewById(R.id.pass_through)
        val name: TextView = iv.findViewById(R.id.pass_name)
        val proofLabel: TextView = iv.findViewById(R.id.pass_extra_label)
        val proof: TextView = iv.findViewById(R.id.pass_extra)
    }

}