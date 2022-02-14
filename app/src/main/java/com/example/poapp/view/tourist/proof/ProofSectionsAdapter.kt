package com.example.poapp.view.tourist.proof

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.RouteViewModel

class ProofSectionsAdapter(
    private val values: List<RouteSection>,
    private val mViewModel: RouteViewModel
) :
    RecyclerView.Adapter<ProofSectionsAdapter.ProofSectionItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProofSectionsAdapter.ProofSectionItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_mountain_pass_item, parent, false)
        return ProofSectionItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProofSectionsAdapter.ProofSectionItemHolder, position: Int) {
        val item = values[position]

        holder.points.visibility = View.GONE
        holder.pointsLabel.visibility = View.GONE
        holder.through.visibility = View.GONE
        holder.throughLabel.visibility = View.GONE
        holder.name.visibility = View.GONE
        holder.nameLabel.visibility = View.GONE
        holder.extra.visibility = View.GONE
        holder.extraLabel.visibility = View.GONE

        holder.start.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        holder.startLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        holder.start.text = mViewModel.getStartPointName(item)

        holder.end.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        holder.endLabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        holder.end.text = mViewModel.getEndPointName(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ProofSectionItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val start: TextView = iv.findViewById(R.id.pass_start)
        val startLabel: TextView = iv.findViewById(R.id.pass_start_label)
        val end: TextView = iv.findViewById(R.id.pass_end)
        val endLabel: TextView = iv.findViewById(R.id.pass_end_label)
        val points: TextView = iv.findViewById(R.id.pass_points)
        val pointsLabel: TextView = iv.findViewById(R.id.pass_points_label)
        val through: TextView = iv.findViewById(R.id.pass_through)
        val throughLabel: TextView = iv.findViewById(R.id.pass_through_label)
        val name: TextView = iv.findViewById(R.id.pass_name)
        val nameLabel: TextView = iv.findViewById(R.id.pass_name_label)
        val extra: TextView = iv.findViewById(R.id.pass_extra)
        val extraLabel: TextView = iv.findViewById(R.id.pass_extra_label)
    }

}