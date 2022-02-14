package com.example.poapp.view.leader

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentConfirmRouteBinding
import com.example.poapp.viewModel.ConfirmRouteViewModel

class ConfirmRouteFragment : Fragment() {

    private var _binding: FragmentConfirmRouteBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: ConfirmRouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmRouteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmRouteEndValue.text = mViewModel.getEndNameForRoute()
        binding.confirmRouteStartValue.text = mViewModel.getStartNameForRoute()
        binding.confirmRoutePointsValue.text = mViewModel.route.value?.punkty.toString()
        binding.touristName.text = mViewModel.getTouristName()
        binding.routeDate.text = mViewModel.route.value!!.dataPrzejscia
        binding.confirmRouteMountainGroupValue.text = mViewModel.getMountainGroupName()

        binding.showOnMap.setOnClickListener {
            backPressed(MapFragment())
        }
        binding.confirmRoute.setOnClickListener {
            val alertDialog = requireActivity().let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        mViewModel.confirmRoute()
                        activity?.supportFragmentManager?.popBackStack()
                    }
                    setNegativeButton(R.string.back) { dialog, _ ->
                        dialog.dismiss()
                    }
                    setTitle(R.string.alert)
                    setMessage(R.string.confirm_route_confirmation_message)
                }
                builder.create()
            }
            alertDialog.show()
            return@setOnClickListener
        }
        binding.showRouteProofs.setOnClickListener {
            backPressed(ListProofsFragment())
        }
        binding.showRouteSections.setOnClickListener {
            backPressed(RouteSectionsListFragment())
        }
        binding.rejectRoute.setOnClickListener {
            val alertDialog = requireActivity().let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        mViewModel.rejectRoute()
                        activity?.supportFragmentManager?.popBackStack()
                    }
                    setNegativeButton(R.string.back) { dialog, _ ->
                        dialog.dismiss()
                    }
                    setTitle(R.string.alert)
                    setMessage(R.string.confirm_route_rejection_message)
                }
                builder.create()
            }
            alertDialog.show()
            return@setOnClickListener
        }
    }

    private fun backPressed(fragment: Fragment){
        activity?.supportFragmentManager?.popBackStack("ConfirmRoute", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(
                R.id.nav_host_fragment_activity_confirm,
                fragment
            )
            ?.addToBackStack("ConfirmRoute")
            ?.commit()
    }
}