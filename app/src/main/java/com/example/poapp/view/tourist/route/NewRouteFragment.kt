package com.example.poapp.view.tourist.route

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.view.tourist.proof.AddProofFragment
import com.example.poapp.viewModel.RouteViewModel

class NewRouteFragment(private val routeId: Int?) : Fragment() {

    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (routeId != null)
            mViewModel.setRoute(routeId)
        else
            mViewModel.saveRoute()

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mViewModel.removeRoute()
            activity?.supportFragmentManager?.popBackStack("RouteDetails", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    RouteListFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_route, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val official = view.findViewById<Button>(R.id.official_button)
        val own = view.findViewById<Button>(R.id.own_button)

        view.findViewById<Button>(R.id.add_mountain_pass_button).setOnClickListener {
            official.visibility = View.VISIBLE
            own.visibility = View.VISIBLE
        }

        view.findViewById<Button>(R.id.add_proof_button).setOnClickListener {
            if (mViewModel.getAllRouteSections().isEmpty()) {
                showAddRouteSectionDialog()
                return@setOnClickListener
            }
            if (mViewModel.getRouteSectionsWithoutProof().isEmpty()) {
                dialogAllSectionsHaveProofs()
                return@setOnClickListener
            }
            Toast.makeText(activity, R.string.extension_point_label, Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    AddProofFragment(true)
                )
                ?.addToBackStack("null")
                ?.commit()
        }

        view.findViewById<Button>(R.id.end_button).setOnClickListener {
            if (mViewModel.getAllRouteSections().isEmpty()) {
                showAddRouteSectionDialog()
                return@setOnClickListener
            }
            activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    SaveRouteFragment()
                )
                ?.addToBackStack("null")
                ?.commit()
        }

        official.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    PickMountainPassFragment(true, mViewModel.route.value!!.id, mViewModel.getLastSection())
                )
                ?.addToBackStack("NewRoute")
                ?.commit()
        }

        own.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    PickMountainPassFragment(false, mViewModel.route.value!!.id, mViewModel.getLastSection())
                )
                ?.addToBackStack("NewRoute")
                ?.commit()
        }

        val list = view.findViewById<RecyclerView>(R.id.route_section_list)
        val allRouteSections = mViewModel.getAllRouteSections()
        list.adapter = RouteSectionAdapter(activity as Context, allRouteSections, mViewModel)
    }

    private fun showAddRouteSectionDialog() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(R.string.no_passes_message)
            }
            builder.create()
        }
        alertDialog.show()
        return
    }

    private fun dialogAllSectionsHaveProofs() {
        val alertDialog = requireActivity().let {
            val builder = AlertDialog.Builder(it)
            builder.apply {
                setNeutralButton(R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                }
                setTitle(R.string.alert)
                setMessage(getString(R.string.no_section_without_proof))
            }
            builder.create()
        }
        alertDialog.show()
    }
}