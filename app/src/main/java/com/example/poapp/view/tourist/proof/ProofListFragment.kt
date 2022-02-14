package com.example.poapp.view.tourist.proof

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentProofListBinding
import com.example.poapp.view.tourist.route.NewRouteFragment
import com.example.poapp.viewModel.RouteViewModel

class ProofListFragment(private val new: Boolean = false) : Fragment() {

    private var _binding: FragmentProofListBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mViewModel.deleteUnconfirmedProofs()
            close()
        }

        _binding = FragmentProofListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.visibility = View.GONE
        binding.cancelSaveProofs.setOnClickListener {
            dialogCancel()
        }
        binding.saveProofs.setOnClickListener {
            mViewModel.confirmProofs()
            close()
        }
        binding.proofList.adapter = ProofListAdapter(activity as Context, mViewModel.proofsNotConfirmed, mViewModel)

    }

    private fun dialogCancel() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setPositiveButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    mViewModel.deleteUnconfirmedProofs()
                    close()
                }
                setNegativeButton(R.string.back) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.confirm_cancel_message)
            }
            builder.create()
        }
        alertDialog.show()
    }

    private fun close() {
        if (new) {
            activity?.supportFragmentManager?.popBackStack("NewRoute", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    NewRouteFragment(mViewModel.route.value!!.id)
                )
                ?.addToBackStack(null)
                ?.commit()
        } else {
            mViewModel.deleteUnconfirmedProofs()
            activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    EditProofsFragment(mViewModel.route.value!!.id.toLong())
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

}