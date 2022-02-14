package com.example.poapp.view.tourist.proof

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentEditProofsBinding
import com.example.poapp.viewModel.RouteViewModel

class EditProofsFragment(private val routeId: Long) : Fragment() {

    private var _binding: FragmentEditProofsBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditProofsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.setRoute(routeId.toInt())
        binding.addProof.setOnClickListener {
            if (mViewModel.getRouteSectionsWithoutProof().isEmpty()) {
                dialogAllSectionsHaveProofs()
                return@setOnClickListener
            }
            activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    AddProofFragment()
                )
                ?.addToBackStack("EditProofs")
                ?.commit()
        }
        binding.deleteProof.setOnClickListener {
            if (mViewModel.getRouteProofs().isEmpty()) {
                dialogNoProofs()
            } else {
                activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment_activity_save_route,
                        DeleteProofsFragment()
                    )
                    ?.addToBackStack("EditProofs")
                    ?.commit()
            }
        }
        binding.seeProofs.setOnClickListener {
            if (mViewModel.getRouteProofs().isEmpty()) {
                dialogNoProofs()
            } else {
                activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment_activity_save_route,
                        ShowProofsFragment()
                    )
                    ?.addToBackStack("EditProofs")
                    ?.commit()
            }
        }
    }

    private fun dialogNoProofs() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.no_proofs_message)
            }
            builder.create()
        }
        alertDialog.show()
    }

    private fun dialogAllSectionsHaveProofs() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(getString(R.string.no_section_without_proof))
            }
            builder.create()
        }
        alertDialog.show()
    }
}