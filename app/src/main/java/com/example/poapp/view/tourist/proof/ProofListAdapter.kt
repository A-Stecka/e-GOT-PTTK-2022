package com.example.poapp.view.tourist.proof

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.Proof
import com.example.poapp.viewModel.RouteViewModel

class ProofListAdapter(
    private val context: Context,
    private val values: List<Proof>,
    private val mViewModel: RouteViewModel,
    private val onProofSelectedListener: OnProofSelectedListener? = null
) :
    RecyclerView.Adapter<ProofListAdapter.ProofItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProofListAdapter.ProofItemHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sections_proof_item, parent, false)
        return ProofItemHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProofItemHolder, position: Int) {
        val item = values[position]
        if (item.FKprzodownik == null) {
            holder.leaderId.visibility = View.GONE
            holder.leaderIdLabel.visibility = View.GONE
            holder.leaderName.visibility = View.GONE
            holder.leaderLabel.visibility = View.GONE
            holder.proofImage.setImageBitmap(mViewModel.getImage(item.zdjecie))
        } else {
            holder.proofImage.visibility = View.GONE
            holder.leaderName.text = mViewModel.getLeaderName(item.FKprzodownik.toLong())
            holder.leaderId.text = mViewModel.getLeader(item.FKprzodownik.toLong())?.nrLegitymacji.toString()
        }
        holder.recyclerView.adapter = ProofSectionsAdapter(mViewModel.getRouteSectionsForProof(item), mViewModel)

        if (onProofSelectedListener != null) {
            holder.itemView.setOnClickListener {
                if (holder.selected.isChecked) {
                    holder.selected.isChecked = false
                    holder.itemView.setBackgroundColor(context.getColor(R.color.white))
                    onProofSelectedListener.uncheck(item.id.toLong())
                } else {
                    holder.selected.isChecked = true
                    holder.itemView.setBackgroundColor(context.getColor(R.color.blue_1))
                    onProofSelectedListener.check(item.id.toLong())
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ProofItemHolder(iv: View) : RecyclerView.ViewHolder(iv) {
        val proofImage: ImageView = iv.findViewById(R.id.proof_image)
        val recyclerView: RecyclerView = iv.findViewById(R.id.proof_sections_list)
        val leaderLabel: TextView = iv.findViewById(R.id.leader_label)
        val leaderName: TextView = iv.findViewById(R.id.leader_name_value)
        val leaderIdLabel: TextView = iv.findViewById(R.id.leader_id_label)
        val leaderId: TextView = iv.findViewById(R.id.leader_id_value)
        val selected: SwitchCompat = iv.findViewById(R.id.proof_selected)
    }

}