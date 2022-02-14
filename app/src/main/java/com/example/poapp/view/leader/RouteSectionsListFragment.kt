package com.example.poapp.view.leader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.ConfirmRouteViewModel

class RouteSectionsListFragment : Fragment() {

    private val mViewModel: ConfirmRouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_route_sections_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.close_route_sections).setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        view.findViewById<RecyclerView>(R.id.route_sections_small_list).adapter =
            RouteSectionSmallAdapter(mViewModel.getRouteSectionsForRoute(), mViewModel, object : OnSectionClickedListener {
                override fun onClick(routeSection: RouteSection) {
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(
                            R.id.nav_host_fragment_activity_confirm,
                            RouteSectionDetailsFragment(routeSection)
                        )
                        ?.addToBackStack(null)
                        ?.commit()
                }
            })
    }
}