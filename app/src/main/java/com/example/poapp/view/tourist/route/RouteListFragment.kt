package com.example.poapp.view.tourist.route

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.Route
import com.example.poapp.view.MainActivity
import com.example.poapp.viewModel.RouteViewModel

class RouteListFragment : Fragment() {

    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.supportFragmentManager?.popBackStack("null", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
        })
        return inflater.inflate(R.layout.fragment_route_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list = view.findViewById<RecyclerView>(R.id.last_routes_list)
        list.layoutManager = LinearLayoutManager(context)
        var allRoutes = emptyList<Route>()
        mViewModel.getAllRoutes(1).observe(viewLifecycleOwner, { passes ->
            passes?.let { allRoutes = it }
            list.adapter = RouteAdapter(activity as Context, allRoutes, mViewModel, object : OnRouteClickedListener {
                    override fun onItemClick(item: Route) {
                        activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(
                                R.id.nav_host_fragment_activity_save_route,
                                RouteDetailsFragment(item.id.toLong())
                            )
                            ?.addToBackStack("RouteList")
                            ?.commit()
                    }
                }
            )
        })

        view.findViewById<Button>(R.id.add_route_button).setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.nav_host_fragment_activity_save_route, NewRouteFragment(null))
                ?.addToBackStack("RouteList")
                ?.commit()
        }
    }
}