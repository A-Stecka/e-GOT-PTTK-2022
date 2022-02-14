package com.example.poapp.view.tourist.route

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.Utils
import com.example.poapp.viewModel.RouteViewModel
import java.text.SimpleDateFormat
import java.util.*

class SaveRouteFragment : Fragment() {

    private val mViewModel: RouteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_save_route, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val points = mViewModel.updateRoutePoints()
        val dateMillis = view.findViewById<CalendarView>(R.id.calendar).date

        val formatter = SimpleDateFormat("yyyy-MM-dd")
        var date = formatter.format(dateMillis)

        view.findViewById<CalendarView>(R.id.calendar).setOnDateChangeListener { _, year, month, dayOfMonth ->
            date = Utils.formatDate(year, month, dayOfMonth)
        }
        view.findViewById<TextView>(R.id.points_sum).text = points.toString()

        view.findViewById<Button>(R.id.cancel_save_route).setOnClickListener {
            val alertDialog = requireActivity().let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton(R.string.ok) { dialog, _ ->
                        dialog.dismiss()
                        mViewModel.removeRoute()
                        activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(
                                R.id.nav_host_fragment_activity_save_route,
                                RouteListFragment()
                            )
                            ?.addToBackStack(null)
                            ?.commit()
                    }
                    setNegativeButton(R.string.back) { dialog, _ ->
                        dialog.dismiss()
                    }
                    setTitle(R.string.alert)
                    setMessage(R.string.confirm_cancel_message)
                }
                builder.create()
            }
            alertDialog.show()
            return@setOnClickListener
        }

        view.findViewById<Button>(R.id.save_route_btn).setOnClickListener {
            mViewModel.route.value!!.dataPrzejscia = date
            mViewModel.updateRoute()
            activity?.supportFragmentManager?.popBackStack("RouteList", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_save_route,
                    RouteListFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

}