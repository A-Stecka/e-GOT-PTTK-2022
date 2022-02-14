package com.example.poapp.viewModel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.poapp.Utils
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.entity.OfficialPoint
import com.example.poapp.model.entity.Proof
import com.example.poapp.model.entity.Route
import com.example.poapp.model.entity.RouteSection
import com.example.poapp.model.repository.*
import com.google.android.gms.maps.model.LatLng

class ConfirmRouteViewModel(application: Application) : AndroidViewModel(application) {

    private val routeViewModel: RouteViewModel
    private val mountainPassOfficialRepository: MountainPassOfficialRepository
    private val officialPointRepository: OfficialPointRepository
    private val mountainPassProofRepository: MountainPassProofRepository
    private val mountainPassUserRepository: MountainPassUserRepository
    private val userPointRepository: UserPointRepository
    private val mountainGroupRepository: MountainGroupRepository
    private val proofRepository: ProofRepository
    private val mountainRangeRepository: MountainRangeRepository
    private val routeSectionRepository: RouteSectionRepository
    private val routeRepository: RouteRepository
    private val touristRepository: TouristRepository
    private val userRepository: UserRepository
    private val leaderQualificationsRepository: LeaderQualificationsRepository
    var leaderId = 0
    val route =
        MutableLiveData(Route(0, 1, "", "oczekuje na wysłanie", 0))

    init {
        val database = AppDatabase.getInstance(application)
        routeViewModel = RouteViewModel(application)
        mountainPassOfficialRepository = MountainPassOfficialRepository(database.mountainPassOfficialDAO())
        mountainPassUserRepository = MountainPassUserRepository(database.mountainPassUserDAO())
        officialPointRepository = OfficialPointRepository(database.officialPointDAO())
        userPointRepository = UserPointRepository(database.userPointDAO())
        proofRepository = ProofRepository(database.proofDAO())
        userRepository = UserRepository(database.userDAO())
        touristRepository = TouristRepository(database.touristDAO())
        mountainPassProofRepository = MountainPassProofRepository(database.mountainPassProofDAO())
        mountainRangeRepository = MountainRangeRepository(database.mountainRangeDAO())
        routeSectionRepository = RouteSectionRepository(database.routeSectionDAO())
        routeRepository = RouteRepository(database.routeDAO())
        leaderQualificationsRepository = LeaderQualificationsRepository(database.leaderQualificationsDAO())
        mountainGroupRepository = MountainGroupRepository(database.mountainGroupDAO())
    }

    fun getTouristName(routeId: Long = route.value!!.id.toLong()): String {
        val tourist = touristRepository.getTourist(routeRepository.getRoute(routeId.toInt())[0].FKturysta.toLong())
        val user = userRepository.getUser(tourist.FKuzytkownik.toLong())[0]
        return user.imie + " " + user.nazwisko
    }

    fun getStartNameForRoute(routeId: Int = route.value!!.id): String {
        return routeViewModel.getStartNameForRoute(routeId)
    }

    fun getEndNameForRoute(routeId: Int = route.value!!.id): String {
        return routeViewModel.getEndNameForRoute(routeId)
    }

    fun getOfficialPoint(id: Int): OfficialPoint {
        return officialPointRepository.getOfficialPoint(id)[0]
    }

    fun getSectionName(routeSection: RouteSection): String {
        return if (routeSection.FKodcinekOficjalny != null) {
            mountainPassOfficialRepository.geMountainPass(routeSection.FKodcinekOficjalny!!)[0].nazwa
        } else {
            mountainPassUserRepository.geMountainPass(routeSection.FKodcinekWlasny!!)[0].nazwa
        }
    }

    fun getSectionStartName(routeSection: RouteSection): String {
        return routeViewModel.getStartPointName(routeSection)
    }

    fun getSectionThroughName(routeSection: RouteSection): String {
        return routeViewModel.getThroughPointName(routeSection)
    }

    fun getSectionEndName(routeSection: RouteSection): String {
        return routeViewModel.getEndPointName(routeSection)
    }

    fun getSectionPoints(routeSection: RouteSection): Int {
        return routeViewModel.getRouteSectionPoints(routeSection)
    }

    fun getRouteSectionsForRoute(routeId: Int = route.value!!.id): List<RouteSection> {
        return routeViewModel.getAllRouteSections(routeId)
    }

    fun getSectionProof(routeSection: RouteSection): Proof? {
        val proofs = mountainPassProofRepository.proofsFor(routeSection.id.toLong())
        for (proof in proofs) {
            if (proofRepository.getProof(proof.FKdowod.toLong()).FKprzodownik != null) {
                return proofRepository.getProof(proof.FKdowod.toLong())
            }
        }
        if (proofs.isEmpty()) {
            return null
        }
        return proofRepository.getProof(proofs[0].FKdowod.toLong())
    }

    fun getLeaderName(leaderId: Int): String {
        return routeViewModel.getLeaderName(leaderId.toLong())
    }

    fun getStartCoordinatesForSection(routeSection: RouteSection): LatLng {
        if (routeSection.FKodcinekOficjalny != null) {
            val point = getOfficialPoint(routeViewModel.getOfficialPass(routeSection.FKodcinekOficjalny!!).FKpunktPoczatkowy)
            return LatLng(point.szerokoscGeo, point.dlugoscGeo)
        }
        val pass = routeViewModel.getUserPass(routeSection.FKodcinekWlasny!!)
        if (pass.FKpunktPoczatkowyWlasny != null) {
            val point = routeViewModel.getUserPoint(pass.FKpunktPoczatkowyWlasny)
            return LatLng(point.szerokoscGeo, point.dlugoscGeo)
        }
        val point = routeViewModel.getOfficialPoint(pass.FKpunktPoczatkowyOficjalny!!)
        return LatLng(point.szerokoscGeo, point.dlugoscGeo)
    }

    fun getEndCoordinatesForSection(routeSection: RouteSection): LatLng {
        if (routeSection.FKodcinekOficjalny != null) {
            val point = getOfficialPoint(routeViewModel.getOfficialPass(routeSection.FKodcinekOficjalny!!).FKpunktKoncowy)
            return LatLng(point.szerokoscGeo, point.dlugoscGeo)
        }
        val pass = routeViewModel.getUserPass(routeSection.FKodcinekWlasny!!)
        if (pass.FKpunktKoncowyWlasny != null) {
            val point = routeViewModel.getUserPoint(pass.FKpunktKoncowyWlasny)
            return LatLng(point.szerokoscGeo, point.dlugoscGeo)
        }
        val point = routeViewModel.getOfficialPoint(pass.FKpunktKoncowyOficjalny!!)
        return LatLng(point.szerokoscGeo, point.dlugoscGeo)
    }

    fun getStartCoordinates(): LatLng {
        return getStartCoordinatesForSection(routeViewModel.getFirstSection(route.value!!.id)!!)
    }

    fun getEndCoordinates(): LatLng {
        return getStartCoordinatesForSection(routeViewModel.getLastSection(route.value!!.id)!!)
    }

    fun getImage(byteArray: ByteArray): Bitmap? {
        return Utils.getImage(byteArray)
    }

    fun getMountainGroupName(routeId: Long = route.value!!.id.toLong()): String {
        val pass = getRouteSectionsForRoute(routeId.toInt())[0]
        if (pass.FKodcinekOficjalny != null) {
            return mountainGroupRepository.getMountainGroup(routeViewModel.getOfficialPass(pass.FKodcinekOficjalny!!).FKpasmoGorskie)[0].nazwa
        }
        return mountainGroupRepository.getMountainGroup(routeViewModel.getUserPass(pass.FKodcinekWlasny!!).FKpasmoGorskie)[0].nazwa
    }

    private fun getLeaderMountainGroupsIDs(): List<Int> {
        val qualifications = leaderQualificationsRepository.getQualificationsForLeader(leaderId)
        val groupsIDs = mutableListOf<Int>()
        for (qualification in qualifications) {
            groupsIDs.add(qualification.FKgrupaGorska)
        }
        return groupsIDs
    }

    private fun getRouteSectionMountainGroupID(routeSection: RouteSection): Long {
        return if (routeSection.FKodcinekOficjalny != null) {
            val pass = mountainPassOfficialRepository.geMountainPass(routeSection.FKodcinekOficjalny!!)
            val range = mountainRangeRepository.getMountainRange(pass[0].FKpasmoGorskie)
            range[0].FKgrupaGorska
        } else {
            val pass = mountainPassOfficialRepository.geMountainPass(routeSection.FKodcinekWlasny!!)
            val range = mountainRangeRepository.getMountainRange(pass[0].FKpasmoGorskie)
            range[0].FKgrupaGorska
        }
    }

    fun routesToConfirmForLeader(): List<Route> {
        val forConfirmation = routeRepository.getForConfirmation()
        val forLeader = mutableListOf<Route>()
        val groups = getLeaderMountainGroupsIDs()
        for (route in forConfirmation) {
            val sections = routeSectionRepository.getRouteSectionForRoute(route.id)
            if (sections.isNotEmpty()) {
                if (getRouteSectionMountainGroupID(sections[0]).toInt() in groups) {
                    forLeader.add(route)
                }
            }
        }
        return forLeader
    }

    fun confirmRoute() {
        route.value?.status = "zatwierdzona"
        routeRepository.update(route.value!!)
    }

    fun rejectRoute() {
        route.value?.status = "odrzucona"
        routeRepository.update(route.value!!)
    }
}