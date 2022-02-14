package com.example.poapp.view.tourist.proof

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.poapp.R
import com.example.poapp.databinding.FragmentAddProofBinding

class AddProofFragment(private val new: Boolean = false) : Fragment() {

    private var _binding: FragmentAddProofBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddProofBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addImage.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("AddProof", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    AddImageSectionsListFragment(new)
                )
                ?.addToBackStack("AddProof")
                ?.commit()
        }
        binding.addLeader.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("AddProof", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    AddProofLeaderFragment(new)
                )
                ?.addToBackStack("AddProof")
                ?.commit()
        }
    }

}