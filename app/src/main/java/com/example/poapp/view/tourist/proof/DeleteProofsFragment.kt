package com.example.poapp.view.tourist.proof

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentProofListBinding
import com.example.poapp.viewModel.RouteViewModel

class DeleteProofsFragment : Fragment() {

    private var _binding: FragmentProofListBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: RouteViewModel by activityViewModels()
    private val selectedProofs = mutableListOf<Long>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProofListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.text = getString(R.string.delete)
        binding.close.setBackgroundColor(requireActivity().getColor(R.color.red))
        binding.close.setOnClickListener {
            if (selectedProofs.isEmpty()) {
                dialogEmptySelection()
            } else {
                dialogConfirmDelete()
            }
        }
        binding.cancelSaveProofs.visibility = View.GONE
        binding.saveProofs.visibility = View.GONE

        binding.proofList.adapter = ProofListAdapter(activity as Context, mViewModel.getRouteProofs(),
            mViewModel, object : OnProofSelectedListener {
                override fun check(proofId: Long) {
                    selectedProofs.add(proofId)
                }

                override fun uncheck(proofId: Long) {
                    selectedProofs.remove(proofId)
                }
            })
    }

    private fun dialogEmptySelection() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.no_proof_selected_message)
            }
            builder.create()
        }
        alertDialog.show()
    }

    private fun dialogConfirmDelete() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    mViewModel.removeProofs(selectedProofs)
                    activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.nav_host_fragment_activity_save_route,
                            EditProofsFragment(mViewModel.route.value!!.id.toLong())
                        )
                        ?.addToBackStack("EditProofs")
                        ?.commit()
                }
                setNegativeButton(R.string.back) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.confirm_delete_message)
            }
            builder.create()
        }
        alertDialog.show()
    }

}