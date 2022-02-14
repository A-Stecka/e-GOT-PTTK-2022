package com.example.poapp.view.member

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentNewOfficialPointBinding
import com.example.poapp.model.entity.OfficialPoint
import com.example.poapp.viewModel.MountainPassOfficialViewModel

//if id != 0 then update existing MountainPass, else add new MountainPass
//changeNr - 0 starting point, 1 point through, 2 end point
class NewOfficialPointFragment(private val officialPointID: Int, private val changeNr: Int) : Fragment() {
    private var _binding: FragmentNewOfficialPointBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: MountainPassOfficialViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewOfficialPointBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var officialPoint = OfficialPoint(0, "", 0.0, 0.0, 0)

        //if id != 0 find existing MountainPass
        if (officialPointID != 0) {
            officialPoint = mViewModel.getOfficialPoint(officialPointID)[0]
            val mountainRange = mViewModel.getMountainRange(officialPoint.FKpasmoGorskie)[0]
            binding.mountainRange.setText(mountainRange.nazwa)
            val mountainGroup = mViewModel.getMountainGroup(mountainRange.FKgrupaGorska.toInt())[0]
            binding.mountainGroup.setText(mountainGroup.nazwa)
        }
        binding.officialPointName.setText(officialPoint.nazwa)
        binding.longitude.setText(officialPoint.dlugoscGeo.toString())
        binding.latitude.setText((officialPoint.szerokoscGeo.toString()))

        //if id != 0 update MountainPass in database, else add new MountainPass
        binding.saveOfficialPoint.setOnClickListener {
            officialPoint.nazwa = binding.officialPointName.text.toString()
            officialPoint.dlugoscGeo = binding.longitude.text.toString().toDouble()
            officialPoint.szerokoscGeo = binding.latitude.text.toString().toDouble()

            if (officialPoint.nazwa == "") {
                val alertDialog = requireActivity().let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setNeutralButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setTitle(R.string.alert)
                        setMessage(R.string.no_point_name_message)
                    }
                    builder.create()
                }
                alertDialog.show()
                return@setOnClickListener
            }

            val mountainRangeList = mViewModel.getMountainRange(binding.mountainRange.text.toString())
            if (mountainRangeList.isNotEmpty()) {
                val mountainRange = mountainRangeList[0]
                officialPoint.FKpasmoGorskie = mountainRange.id
            } else {
                //MountainGroup and MountainRange have to exist in database
                val alertDialog = requireActivity().let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setNeutralButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setTitle(R.string.alert)
                        setMessage(R.string.mountain_range_error_message)
                    }
                    builder.create()
                }
                alertDialog.show()
                return@setOnClickListener
            }

            //update MountainPass in database or add new MountainPass
            //each RouteSection has its own points - handling user points not implemented (different use case)
            if (officialPoint.id == 0)
                officialPoint.id = mViewModel.addMountainPass(officialPoint).toInt()
            else
                mViewModel.updateOfficialPoint(officialPoint)

            //change data in ViewModel
            when (changeNr) {
                0 -> mViewModel.mountainPassOfficial.value!!.FKpunktPoczatkowy = officialPoint.id
                1 -> mViewModel.mountainPassOfficial.value!!.FKpunktPosredni = officialPoint.id
                else -> mViewModel.mountainPassOfficial.value!!.FKpunktKoncowy = officialPoint.id
            }
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}