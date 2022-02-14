package com.example.poapp.view.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.poapp.databinding.FragmentEditMountainPassNameBinding
import com.example.poapp.viewModel.MountainPassOfficialViewModel

class EditMountainPassNameFragment : Fragment() {
    private var _binding: FragmentEditMountainPassNameBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: MountainPassOfficialViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditMountainPassNameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editMountainPassName.setText(mViewModel.mountainPassOfficial.value!!.nazwa)
        binding.saveMountainPassName.setOnClickListener {
            mViewModel.mountainPassOfficial.value!!.nazwa = binding.editMountainPassName.text.toString()
            activity?.supportFragmentManager?.popBackStack()
        }
    }

}