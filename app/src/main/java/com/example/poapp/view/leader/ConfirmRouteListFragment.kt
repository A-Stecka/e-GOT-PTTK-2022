package com.example.poapp.view.leader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentConfirmRouteListBinding
import com.example.poapp.model.entity.Route
import com.example.poapp.view.tourist.route.OnRouteClickedListener
import com.example.poapp.viewModel.ConfirmRouteViewModel

class ConfirmRouteListFragment : Fragment() {

    private var _binding: FragmentConfirmRouteListBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: ConfirmRouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentConfirmRouteListBinding.inflate(inflater, container, false)
        mViewModel.leaderId = 1
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmRoutesList.adapter = RoutesToConfirmAdapter(mViewModel.routesToConfirmForLeader(),
            mViewModel, object : OnRouteClickedListener {
                override fun onItemClick(item: Route) {
                    mViewModel.route.value = item
                    activity?.supportFragmentManager?.popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.nav_host_fragment_activity_confirm,
                            ConfirmRouteFragment()
                        )
                        ?.addToBackStack("ConfirmRouteList")
                        ?.commit()
                }

            })


    }
}




