package com.example.poapp.view.leader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.viewModel.ConfirmRouteViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private val mViewModel: ConfirmRouteViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val start = mViewModel.getStartCoordinates()
        val end = mViewModel.getEndCoordinates()
        mMap.addMarker(
            MarkerOptions()
                .position(start)
                .title("Start")
        )
        mMap.addMarker(
            MarkerOptions()
                .position(end)
                .title("End")
        )
        mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
        mMap.addPolyline(PolylineOptions().add(start, end))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(start, 13f))
    }
}