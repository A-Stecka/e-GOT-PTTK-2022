package com.example.poapp.view.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentMountainPassDetailsBinding
import com.example.poapp.viewModel.MountainPassOfficialViewModel

class MountainPassDetailsFragment(private val mountainPassId: Int) : Fragment() {
    private var _binding: FragmentMountainPassDetailsBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: MountainPassOfficialViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMountainPassDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mountainPass = mViewModel.getMountainPassOfficial(mountainPassId)[0]

        binding.startPoint.text = mViewModel.getOfficialPoint(mountainPass.FKpunktPoczatkowy)[0].nazwa
        binding.endPoint.text = mViewModel.getOfficialPoint(mountainPass.FKpunktKoncowy)[0].nazwa

        if (mountainPass.FKpunktPosredni != null && mountainPass.FKpunktPosredni != 0)
            binding.pointThrough.text =
                mViewModel.getOfficialPoint(mountainPass.FKpunktPosredni!!)[0].nazwa
        else
            binding.pointThrough.text = "-"

        binding.pointsValue.text = mountainPass.punkty.toString()
        binding.nameValue.text = mountainPass.nazwa
        val mountainRange = mViewModel.getMountainRange(mountainPass.FKpasmoGorskie)[0]
        binding.mountainRangeValue.text = mountainRange.nazwa
        binding.mountainGroupValue.text = mViewModel.getMountainGroup(mountainRange.FKgrupaGorska.toInt())[0].nazwa
        binding.statusValue.text = mountainPass.status

        if (binding.statusValue.text == resources.getString(R.string.active))
            binding.statusValue.setTextColor(requireActivity().getColor(R.color.green))
        else
            binding.statusValue.setTextColor(requireActivity().getColor(R.color.red))

        binding.deleteButton.setOnClickListener {
            Toast.makeText(requireContext(), R.string.not_implemented_label, Toast.LENGTH_SHORT).show()
        }
        binding.editButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("PassDetails", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_mountain_passes_list,
                    NewMountainPassFragment(mountainPass.id)
                )
                ?.addToBackStack("PassDetails")
                ?.commit()
        }
    }
}