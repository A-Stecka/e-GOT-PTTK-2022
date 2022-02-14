package com.example.poapp.view.tourist.route

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.model.entity.MountainPassUser
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.MountainPassListViewModel

class PickMountainPassFragment(private val ifOfficial: Boolean, private val routeId: Int, private val lastSection: RouteSection?) : Fragment() {
    private val mViewModel: MountainPassListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (ifOfficial)
            inflater.inflate(R.layout.fragment_pick_official_mountain_pass, container, false)
        else
            inflater.inflate(R.layout.fragment_pick_own_mountain_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!ifOfficial)
            view.findViewById<Button>(R.id.add_own_mountain_pass).setOnClickListener {
                Toast.makeText(activity, R.string.extension_point_label, Toast.LENGTH_SHORT).show()
            }

        val listener = object : OnMountainPassPickedListener {
            private fun dialog() {
                val alertDialog = requireActivity().let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setNeutralButton(R.string.ok) { dialog, _ ->
                            dialog.dismiss()
                        }
                        setTitle(R.string.alert)
                        setMessage(R.string.previous_end_current_start_message)
                    }
                    builder.create()
                }
                alertDialog.show()
                return
            }

            private fun save() {
                mViewModel.routeId = routeId
                activity?.supportFragmentManager?.popBackStack("NewRoute", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(
                        R.id.nav_host_fragment_activity_save_route,
                        PickedPassPointsFragment()
                    )
                    ?.addToBackStack(null)
                    ?.commit()
            }

            override fun onPassSelected(official: MountainPassOfficial?, user: MountainPassUser?) {
                if (official != null) {
                    mViewModel.routeSection.value?.FKodcinekOficjalny = official.id
                    mViewModel.routeSection.value?.FKodcinekWlasny = null
                }
                if (user != null) {
                    mViewModel.routeSection.value?.FKodcinekWlasny = user.id
                    mViewModel.routeSection.value?.FKodcinekOficjalny = null
                }

                if (lastSection != null) {
                    if (lastSection.FKodcinekOficjalny != null) {
                        val endLast = mViewModel.getOfficialPass(lastSection.FKodcinekOficjalny!!).FKpunktKoncowy
                        if (official != null) {
                            val startNew = official.FKpunktPoczatkowy

                            if (endLast == startNew)
                                save()
                            else
                                dialog()

                        } else {
                            if (user?.FKpunktPoczatkowyOficjalny != null) {
                                val startNew = user.FKpunktPoczatkowyOficjalny

                                if (endLast == startNew)
                                    save()
                                else
                                    dialog()

                            } else
                                dialog()
                        }
                    } else {
                        val pass = mViewModel.getUserPass(lastSection.FKodcinekWlasny!!)

                        if (pass.FKpunktKoncowyOficjalny != null) {
                            val endLast = pass.FKpunktKoncowyOficjalny
                            if (official != null) {
                                val startNew = official.FKpunktPoczatkowy

                                if (endLast == startNew)
                                    save()
                                else
                                    dialog()

                            } else {
                                if (user?.FKpunktPoczatkowyOficjalny != null) {
                                    val startNew = user.FKpunktPoczatkowyOficjalny

                                    if (endLast == startNew)
                                        save()
                                    else
                                        dialog()

                                } else
                                    dialog()
                            }
                        } else {
                            val endLast = pass.FKpunktKoncowyWlasny
                            if (official != null)
                                dialog()
                            else {
                                if (user?.FKpunktPoczatkowyWlasny != null) {
                                    val startNew = user.FKpunktPoczatkowyWlasny

                                    if (endLast == startNew)
                                        save()
                                    else
                                        dialog()

                                } else
                                    dialog()
                            }
                        }
                    }
                } else
                    save()
            }
        }

        val list = view.findViewById<RecyclerView>(R.id.mountain_passes_list_pick)
        if (ifOfficial) {
            var allPasses = emptyList<MountainPassOfficial>()
            mViewModel.getAllOfficialPasses().observe(viewLifecycleOwner, { passes ->
                passes?.let { allPasses = it }
                list.adapter = MountainPassPickAdapter(activity as Context, allPasses, mViewModel, listener)
            })
        } else {
            var allPasses = emptyList<MountainPassUser>()
            mViewModel.getAllUserPasses().observe(viewLifecycleOwner, { passes ->
                passes?.let { allPasses = it }
                list.adapter = MountainPassPickAdapter(activity as Context, allPasses, mViewModel, listener)
            })
        }
    }


}
