package com.example.poapp.view.leader

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.poapp.R
import com.example.poapp.databinding.FragmentRouteSectionDetailsBinding
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.viewModel.ConfirmRouteViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions

class RouteSectionDetailsFragment(private val routeSection: RouteSection) : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentRouteSectionDetailsBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: ConfirmRouteViewModel by activityViewModels()
    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRouteSectionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.sectionDetailsEndValue.text = mViewModel.getSectionEndName(routeSection)
        binding.sectionDetailsStartValue.text = mViewModel.getSectionStartName(routeSection)
        binding.sectionDetailsTimeValue.text = routeSection.czasPrzejscia.toString()
        binding.sectionDetailsThroughValue.text = mViewModel.getSectionThroughName(routeSection)
        if (mViewModel.getSectionName(routeSection) != "")
            binding.sectionDetailsNameValue.text = mViewModel.getSectionName(routeSection)
        binding.sectionDetailsPointsValue.text = mViewModel.getSectionPoints(routeSection).toString()

        val proof = mViewModel.getSectionProof(routeSection)
        when {
            proof == null -> {
                binding.sectionDetailsLeaderNameLabel.visibility = View.GONE
                binding.sectionDetailsProofImage.visibility = View.GONE
                binding.sectionDetailsLeaderNameValue.visibility = View.GONE
                binding.sectionDetailsLeaderLabel.visibility = View.GONE
                binding.sectionDetailsLeaderIdLabel.visibility = View.GONE
                binding.sectionDetailsLeaderIdValue.visibility = View.GONE
            }
            proof.FKprzodownik != null -> {
                binding.sectionDetailsProofImage.visibility = View.GONE
                binding.sectionDetailsLeaderNameValue.text = mViewModel.getLeaderName(proof.FKprzodownik)
                binding.sectionDetailsLeaderIdValue.text = proof.FKprzodownik.toString()
            }
            else -> {
                binding.sectionDetailsLeaderNameLabel.visibility = View.GONE
                binding.sectionDetailsLeaderNameValue.visibility = View.GONE
                binding.sectionDetailsLeaderLabel.visibility = View.GONE
                binding.sectionDetailsLeaderIdLabel.visibility = View.GONE
                binding.sectionDetailsLeaderIdValue.visibility = View.GONE
                binding.sectionDetailsProofImage.setImageBitmap(mViewModel.getImage(proof.zdjecie!!))
            }
        }
        binding.closeSectionDetails.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack("ConfirmRoute", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(
                    R.id.nav_host_fragment_activity_confirm,
                    RouteSectionsListFragment()
                )
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val start = mViewModel.getStartCoordinatesForSection(routeSection)
        val end = mViewModel.getEndCoordinatesForSection(routeSection)
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