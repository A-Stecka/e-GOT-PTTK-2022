package com.example.poapp.view.member

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.viewModel.MountainPassOfficialViewModel

class MountainPassAdapter(
    private val context: Context,
    private val values: List<MountainPassOfficial>,
    private val mViewModel: MountainPassOfficialViewModel,
    private val onMountainPassClickedListener: OnMountainPassClickedListener
) :
    RecyclerView.Adapter<MountainPassAdapter.MountainPassOfficialItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainPassOfficialItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.fragment_mountain_pass_item, parent,
            false
        )
        return MountainPassOfficialItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: MountainPassOfficialItemHolder, position: Int) {
        val mountainPass = values[position]
        holder.startPoint.text = mViewModel.getOfficialPoint(mountainPass.FKpunktPoczatkowy)[0].nazwa
        holder.endPoint.text = mViewModel.getOfficialPoint(mountainPass.FKpunktKoncowy)[0].nazwa
        holder.passPoints.text = mountainPass.punkty.toString()

        if (mountainPass.FKpunktPosredni != null && mountainPass.FKpunktPosredni != 0)
            holder.throughPoint.text = mViewModel.getOfficialPoint(mountainPass.FKpunktPosredni!!)[0].nazwa
        else
            holder.throughPoint.text = "-"

        holder.name.text = mountainPass.nazwa

        holder.statusLabel.text = context.resources.getString(R.string.status)
        holder.status.text = mountainPass.status
        if (holder.status.text == context.resources.getString(R.string.active))
            holder.status.setTextColor(context.getColor(R.color.green))
        else
            holder.status.setTextColor(context.getColor(R.color.red))

        holder.itemView.setOnClickListener {
            onMountainPassClickedListener.onItemClick(mountainPass)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class MountainPassOfficialItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val startPoint: TextView = iv.findViewById(R.id.pass_start)
        val endPoint: TextView = iv.findViewById(R.id.pass_end)
        val passPoints: TextView = iv.findViewById(R.id.pass_points)
        val throughPoint: TextView = iv.findViewById(R.id.pass_through)
        val name: TextView = iv.findViewById(R.id.pass_name)
        val statusLabel: TextView = iv.findViewById(R.id.pass_extra_label)
        val status: TextView = iv.findViewById(R.id.pass_extra)
    }

}