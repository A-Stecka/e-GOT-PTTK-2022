package com.example.poapp.view.tourist.proof

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

class ShowProofsFragment : Fragment() {

    private var _binding: FragmentProofListBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProofListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.close.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("EditProofs", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    EditProofsFragment(mViewModel.route.value!!.id.toLong())
                )
                ?.addToBackStack(null)
                ?.commit()
        }
        binding.cancelSaveProofs.visibility = View.GONE
        binding.saveProofs.visibility = View.GONE

        binding.proofList.adapter = ProofListAdapter(activity as Context, mViewModel.getRouteProofs(), mViewModel)
    }

}