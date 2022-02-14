package com.example.poapp.view.member

import android.os.Bundle
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.poapp.R
import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.viewModel.MountainPassOfficialViewModel

class MountainPassesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_mountain_passes_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mViewModel = ViewModelProviders.of(this)[MountainPassOfficialViewModel::class.java]

        val list = view.findViewById<RecyclerView>(R.id.mountain_passes_list)
        list.layoutManager = LinearLayoutManager(context)
        var allPasses = emptyList<MountainPassOfficial>()
        mViewModel.getAll().observe(viewLifecycleOwner, { passes ->
            passes?.let { allPasses = it }
            list.adapter = MountainPassAdapter(activity as Context, allPasses, mViewModel, object : OnMountainPassClickedListener {
                    override fun onItemClick(item: MountainPassOfficial) {
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(
                                R.id.nav_host_fragment_activity_mountain_passes_list,
                                MountainPassDetailsFragment(item.id)
                            )
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                }
            )
        })

        view.findViewById<Button>(R.id.add_new_pass).setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_mountain_passes_list,
                    NewMountainPassFragment(0)
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}