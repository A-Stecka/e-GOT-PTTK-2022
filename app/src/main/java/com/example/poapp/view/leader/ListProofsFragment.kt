package com.example.poapp.view.leader

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentProofListBinding
import com.example.poapp.view.tourist.proof.ProofListAdapter
import com.example.poapp.viewModel.ConfirmRouteViewModel
import com.example.poapp.viewModel.RouteViewModel

class ListProofsFragment : Fragment() {

    private var _binding: FragmentProofListBinding? = null
    private val binding get() = _binding!!
    private val routeViewModel: RouteViewModel by activityViewModels()
    private val mViewModel: ConfirmRouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProofListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
        binding.cancelSaveProofs.visibility = View.GONE
        binding.saveProofs.visibility = View.GONE

        binding.proofList.adapter =
            ProofListAdapter(
                activity as Context,
                routeViewModel.getRouteProofs(mViewModel.route.value!!.id),
                routeViewModel, null
            )
    }

}