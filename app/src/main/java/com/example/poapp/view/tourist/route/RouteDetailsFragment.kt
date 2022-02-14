package com.example.poapp.view.tourist.route

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.view.tourist.proof.EditProofsFragment
import com.example.poapp.viewModel.RouteViewModel
import com.example.poapp.databinding.FragmentRouteDetailsBinding

class RouteDetailsFragment(private val routeId: Long) : Fragment() {

    private var _binding: FragmentRouteDetailsBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRouteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.setRoute(routeId.toInt())
        val allRouteSection = mViewModel.getAllRouteSections()
        binding.routeSectionList.adapter = RouteSectionAdapter(activity as Context, allRouteSection, mViewModel)

        if (mViewModel.route.value!!.status != getString(R.string.not_send)) {
            binding.editProofs.isEnabled = false
            binding.editRoute.isEnabled = false
            binding.deleteRoute.isEnabled = false
            binding.sendToLeader.isEnabled = false
        }

        binding.editProofs.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    EditProofsFragment(mViewModel.route.value!!.id.toLong())
                )
                ?.addToBackStack("RouteDetails")
                ?.commit()
        }
        binding.editRoute.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_implemented_label), Toast.LENGTH_SHORT).show()
        }
        binding.deleteRoute.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_implemented_label), Toast.LENGTH_SHORT).show()
        }
        binding.sendToLeader.setOnClickListener {

            if (mViewModel.getRouteProofs().isNotEmpty()) {
                val alertDialog = requireActivity().let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                            mViewModel.route.value!!.status = getString(R.string.waiting_for_confirmation)
                            mViewModel.updateRoute()
                            binding.editProofs.isEnabled = false
                            binding.editRoute.isEnabled = false
                            binding.deleteRoute.isEnabled = false
                            binding.sendToLeader.isEnabled = false
                        }
                        setNegativeButton(R.string.back) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setTitle(R.string.alert)
                        setMessage(R.string.confirm_send_to_leader_message)
                    }
                    builder.create()
                }
                alertDialog.show()
                return@setOnClickListener
            } else {
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
                return@setOnClickListener
            }
        }
    }
}