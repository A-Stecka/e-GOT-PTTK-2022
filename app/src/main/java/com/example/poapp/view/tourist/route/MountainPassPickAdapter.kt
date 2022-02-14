package com.example.poapp.view.tourist.route

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.model.entity.MountainPassUser
import com.example.poapp.viewModel.MountainPassListViewModel

class MountainPassPickAdapter<E>(
    private val context: Context,
    private val values: List<E>,
    private val mViewModel: MountainPassListViewModel,
    private val onMountainPassPickedListener: OnMountainPassPickedListener
) :
    RecyclerView.Adapter<MountainPassPickAdapter<E>.MountainPassItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MountainPassItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fragment_mountain_pass_item, parent, false)
        return MountainPassItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: MountainPassItemHolder, position: Int) {
        val item = values[position]

        if (item is MountainPassOfficial) {
            holder.start.text = mViewModel.getOfficialPoint(item.FKpunktPoczatkowy).nazwa
            holder.end.text = mViewModel.getOfficialPoint(item.FKpunktKoncowy).nazwa
            holder.points.text = item.punkty.toString()
            if (item.FKpunktPosredni != null)
                holder.through.text = mViewModel.getOfficialPoint(item.FKpunktPosredni!!).nazwa

            if (item.nazwa != "")
                holder.name.text = item.nazwa

            holder.extraLabel.text = context.resources.getString(R.string.status)
            holder.extra.text = item.status
            if (holder.extra.text == context.resources.getString(R.string.active))
                holder.extra.setTextColor(context.getColor(R.color.green))
            else
                holder.extra.setTextColor(context.getColor(R.color.red))

        } else
            if (item is MountainPassUser) {

                if (item.FKpunktPoczatkowyOficjalny != null)
                    holder.start.text = mViewModel.getOfficialPoint(item.FKpunktPoczatkowyOficjalny).nazwa
                else
                    holder.start.text = mViewModel.getUserPoint(item.FKpunktPoczatkowyWlasny!!).nazwa

                if (item.FKpunktKoncowyOficjalny != null)
                    holder.end.text = mViewModel.getOfficialPoint(item.FKpunktKoncowyOficjalny).nazwa
                else
                    holder.end.text = mViewModel.getUserPoint(item.FKpunktKoncowyWlasny!!).nazwa

                holder.points.text = item.punkty.toString()

                if (item.FKpunktPosredniOficjalny != null)
                    holder.through.text = mViewModel.getOfficialPoint(item.FKpunktPosredniOficjalny).nazwa
                else
                    if (item.FKpunktPosredniWlasny != null)
                        holder.through.text = mViewModel.getUserPoint(item.FKpunktPosredniWlasny).nazwa

                if (item.nazwa != "")
                    holder.name.text = item.nazwa

                holder.extraLabel.visibility = View.GONE
                holder.extra.visibility = View.GONE
            }

        holder.itemView.setOnClickListener {
            if (item is MountainPassOfficial)
                onMountainPassPickedListener.onPassSelected(item, null)
            if (item is MountainPassUser)
                onMountainPassPickedListener.onPassSelected(null, item)
        }
    }

    override fun getItemCount(): Int = values.size

    inner class MountainPassItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val start: TextView = iv.findViewById(R.id.pass_start)
        val end: TextView = iv.findViewById(R.id.pass_end)
        val points: TextView = iv.findViewById(R.id.pass_points)
        val through: TextView = iv.findViewById(R.id.pass_through)
        val name: TextView = iv.findViewById(R.id.pass_name)
        val extraLabel: TextView = iv.findViewById(R.id.pass_extra_label)
        val extra: TextView = iv.findViewById(R.id.pass_extra)
    }
}