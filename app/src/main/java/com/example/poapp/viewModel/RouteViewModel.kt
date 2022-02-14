package com.example.poapp.viewModel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.poapp.Utils
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.entity.*
import com.example.poapp.model.repository.*

class RouteViewModel(application: Application) : AndroidViewModel(application) {

    private val routeRepository: RouteRepository
    private val mountainPassOfficialRepository: MountainPassOfficialRepository
    private val mountainPassUserRepository: MountainPassUserRepository
    private val userPointRepository: UserPointRepository
    private val officialPointRepository: OfficialPointRepository
    private val routeSectionRepository: RouteSectionRepository
    private val mountainPassProofRepository: MountainPassProofRepository
    private val proofRepository: ProofRepository
    private val leaderRepository: LeaderRepository

    val route =
        MutableLiveData(Route(0, 1, "", "oczekuje na wysłanie", 0))
    private var routeSections = listOf<RouteSection>()
    var proofsNotConfirmed = mutableListOf<Proof>()

    init {
        val database = AppDatabase.getInstance(application)
        routeRepository = RouteRepository(database.routeDAO())
        mountainPassUserRepository = MountainPassUserRepository(database.mountainPassUserDAO())
        mountainPassOfficialRepository = MountainPassOfficialRepository(database.mountainPassOfficialDAO())
        routeSectionRepository = RouteSectionRepository(database.routeSectionDAO())
        officialPointRepository = OfficialPointRepository(database.officialPointDAO())
        userPointRepository = UserPointRepository(database.userPointDAO())
        mountainPassProofRepository = MountainPassProofRepository(database.mountainPassProofDAO())
        proofRepository = ProofRepository(database.proofDAO())
        leaderRepository = LeaderRepository(database.leaderDAO())
    }

    fun setRoute(route: Int) {
        this.route.value = routeRepository.getRoute(route)[0]
        routeSections = routeSectionRepository.getRouteSectionForRoute(route)
    }

    fun getLastSection(routeId: Int = route.value!!.id): RouteSection? {
        getAllRouteSections(routeId)
        return if (routeSections.isNotEmpty()) {
            routeSections.last()
        } else {
            null
        }
    }

    fun getFirstSection(routeId: Int = route.value!!.id): RouteSection? {
        getAllRouteSections(routeId)
        return if (routeSections.isNotEmpty()) {
            routeSections.first()
        } else {
            null
        }
    }

    fun saveRoute() {
        route.value = Route(0, 1, "", "oczekuje na wysłanie", 0)
        routeSections = listOf()
        route.value!!.id = routeRepository.insert(route.value!!).toInt()
    }

    fun getAllRouteSections(routeId: Int = route.value!!.id): List<RouteSection> {
        routeSections = routeSectionRepository.getRouteSectionForRoute(routeId)
        return routeSections
    }

    fun getRouteSectionsWithoutProof(): List<RouteSection> {
        getAllRouteSections()
        val result = mutableListOf<RouteSection>()
        for (section in routeSections) {
            if (mountainPassProofRepository.proofsFor(section.id.toLong()).isEmpty()) {
                result.add(section)
            }
        }
        return result
    }

    fun getAllRoutes(idUser: Int): LiveData<List<Route>> {
        return routeRepository.getAllForUser(idUser)
    }

    fun updateRoute() {
        if (route.value != null && route.value!!.id != 0) {
            routeRepository.update(route.value!!)
        }
    }

    fun removeRoute() {
        if (route.value != null && route.value!!.id != 0) {
            val proofsIDs = mutableListOf<Long>()
            for (proof in getRouteProofs())
                proofsIDs.add(proof.id.toLong())
            removeProofs(proofsIDs as List<Long>)
            routeSectionRepository.deleteAllFor(route.value!!.id.toLong())
            routeRepository.delete(route.value!!.id.toLong())
        }
    }

    fun hasProof(routeSection: RouteSection): Boolean {
        if (mountainPassProofRepository.proofsFor(routeSection.id.toLong()).isEmpty()) {
            return false
        }
        return true
    }

    fun updateRoutePoints(): Int {
        var sum = 0
        for (section in routeSections) {
            sum += if (section.FKodcinekOficjalny != null) {
                getOfficialPass(section.FKodcinekOficjalny!!).punkty
            } else {
                getUserPass(section.FKodcinekWlasny!!).punkty
            }
        }
        route.value!!.punkty = sum
        return sum
    }

    fun getStartNameForRoute(routeId: Int): String {
        val routeSection = routeSectionRepository.getRouteSectionForRoute(routeId).first()
        return getStartPointName(routeSection)
    }

    fun getEndNameForRoute(routeId: Int): String {
        val routeSection = routeSectionRepository.getRouteSectionForRoute(routeId).last()
        return getEndPointName(routeSection)
    }

    fun getStartPointName(routeSection: RouteSection): String {
        if (routeSection.FKodcinekOficjalny != null) {
            return getOfficialPoint(getOfficialPass(routeSection.FKodcinekOficjalny!!).FKpunktPoczatkowy).nazwa
        }
        val pass = getUserPass(routeSection.FKodcinekWlasny!!)
        if (pass.FKpunktPoczatkowyWlasny != null) {
            return getUserPoint(pass.FKpunktPoczatkowyWlasny).nazwa
        }
        return getOfficialPoint(pass.FKpunktPoczatkowyOficjalny!!).nazwa
    }

    fun getEndPointName(routeSection: RouteSection): String {
        if (routeSection.FKodcinekOficjalny != null) {
            return getOfficialPoint(getOfficialPass(routeSection.FKodcinekOficjalny!!).FKpunktKoncowy).nazwa
        }
        val pass = getUserPass(routeSection.FKodcinekWlasny!!)
        if (pass.FKpunktKoncowyWlasny != null) {
            return getUserPoint(pass.FKpunktKoncowyWlasny).nazwa
        }
        return getOfficialPoint(pass.FKpunktKoncowyOficjalny!!).nazwa
    }

    fun getThroughPointName(routeSection: RouteSection): String {
        val name: String = if (routeSection.FKodcinekOficjalny != null) {
            getOfficialPass(routeSection.FKodcinekOficjalny!!).FKpunktPosredni?.let { getOfficialPoint(it).nazwa }.toString()
        } else {
            val pass = getUserPass(routeSection.FKodcinekWlasny!!)
            if (pass.FKpunktPosredniWlasny != null) {
                getUserPoint(pass.FKpunktPosredniWlasny).nazwa
            } else {
                pass.FKpunktPosredniOficjalny?.let { getOfficialPoint(it).nazwa }.toString()
            }
        }
        return if (name != "null") {
            name
        } else {
            "-"
        }
    }

    fun getRouteSectionPoints(routeSection: RouteSection): Int {
        if (routeSection.FKodcinekOficjalny != null) {
            return getOfficialPass(routeSection.FKodcinekOficjalny!!).punkty
        }
        return getUserPass(routeSection.FKodcinekWlasny!!).punkty
    }

    fun getRouteSectionName(routeSection: RouteSection): String {
        val name = if (routeSection.FKodcinekOficjalny != null) {
            getOfficialPass(routeSection.FKodcinekOficjalny!!).nazwa
        } else {
            getUserPass(routeSection.FKodcinekWlasny!!).nazwa
        }
        return if (name != "null") {
            name
        } else {
            "-"
        }
    }

    fun getOfficialPass(id: Int): MountainPassOfficial {
        return mountainPassOfficialRepository.geMountainPass(id)[0]
    }

    fun getUserPass(id: Int): MountainPassUser {
        return mountainPassUserRepository.geMountainPass(id)[0]
    }

    fun getUserPoint(id: Int): UserPoint {
        return userPointRepository.getUserPoint(id)[0]
    }

    fun getOfficialPoint(id: Int): OfficialPoint {
        return officialPointRepository.getOfficialPoint(id)[0]
    }


    //0 if correct, -1 if route already has a leader assigned as proof, -2 if there is no such leader
    fun saveLeaderProof(leaderID: Long): Int {
        if (getLeader(leaderID) == null) {
            return -2
        }
        if (route.value?.id?.let { getLeaderForRoute(it.toLong()) } == null) {
            val proof = Proof(0, null, leaderID.toInt())
            val proofId = proofRepository.insert(proof)
            val sections = getRouteSectionsForRoute(route.value!!.id.toLong())
            for (section in sections) {
                val sectionProof = MountainPassProof(0, section.id, proofId.toInt())
                mountainPassProofRepository.insert(sectionProof)
            }
            return 0
        }
        return -1
    }

    private fun getLeaderForRoute(routeId: Long): Long? {
        val proofs = mountainPassProofRepository.proofsFor(getRouteSectionsForRoute(routeId))
        for (proof in proofs) {
            val leader = proofRepository.getProof(proof.FKdowod.toLong()).FKprzodownik
            if (leader != null) {
                return leader.toLong()
            }
        }
        return null
    }

    private fun getRouteSectionsForRoute(routeId: Long): List<RouteSection> {
        return routeSectionRepository.getRouteSectionForRoute(routeId.toInt())
    }

    fun getLeaderName(leaderID: Long): String {
        val user = leaderRepository.getLeaderUser(leaderID)
        return if (user != null) {
            user.imie + " " + user.nazwisko
        } else "Przodownik nie znaleziony"
    }

    fun getLeader(leaderID: Long): Leader? {
        return leaderRepository.getLeader(leaderID)
    }

    fun getRouteSectionsForProof(proof: Proof): List<RouteSection> {
        val sectionsIDs = mountainPassProofRepository.routeSectionsIDsFor(proof.id.toLong())
        return routeSectionRepository.getSections(sectionsIDs)
    }

    fun saveImageProof(routeSectionsIDs: List<Long>, bitmap: Bitmap) {
        val array = getBitmapAsByteArray(bitmap)
        val proof = Proof(0, array, null)
        val proofId = proofRepository.insert(proof)
        proof.id = proofId.toInt()
        proofsNotConfirmed.add(proof)
        for (sectionID in routeSectionsIDs) {
            val sectionProof = MountainPassProof(0, sectionID.toInt(), proofId.toInt())
            mountainPassProofRepository.insert(sectionProof)
        }
    }

    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        return Utils.getBitmapAsByteArray(bitmap)
    }

    fun getImage(byteArray: ByteArray?): Bitmap? {
        return Utils.getImage(byteArray)
    }

    fun deleteUnconfirmedProofs() {
        for (proof in proofsNotConfirmed) {
            mountainPassProofRepository.deleteAllForProof(proof.id)
            proofRepository.delete(proof.id.toLong())
        }
        proofsNotConfirmed.clear()
    }

    fun confirmProofs() {
        proofsNotConfirmed = mutableListOf()
    }

    fun getRouteProofs(routeId: Int = route.value!!.id): List<Proof> {
        val mountainPassProofs = mutableListOf<MountainPassProof>()
        for (section in getAllRouteSections(routeId)) {
            mountainPassProofs += mountainPassProofRepository.proofsFor(section.id.toLong())
        }
        val proofs = mutableListOf<Proof>()
        for (mountainPassProof in mountainPassProofs) {
            proofs.add(proofRepository.getProof(mountainPassProof.FKdowod.toLong()))
        }
        return proofs.distinct()
    }

    fun removeProofs(selectedProofs: List<Long>) {
        for (proofId in selectedProofs) {
            mountainPassProofRepository.deleteAllForProof(proofId.toInt())
            proofRepository.delete(proofId)
        }
    }

    fun cleanInvalid() {
        val routes = routeRepository.getAll()
        for (route in routes) {
            if (route.dataPrzejscia == "" || route.FKturysta == 0 || routeSectionRepository.getRouteSectionForRoute(route.id).isEmpty()) {
                val proofsISs = mutableListOf<Int>()
                for (section in getRouteSectionsForRoute(route.id.toLong())) {
                    for (passProof in mountainPassProofRepository.proofsFor(section.id.toLong())) {
                        proofsISs.add(passProof.id)
                        mountainPassProofRepository.delete(passProof.id)
                    }
                    for (id in proofsISs) {
                        proofRepository.delete(id.toLong())
                    }
                }
                routeSectionRepository.deleteAllFor(route.id.toLong())
                routeRepository.delete(route.id.toLong())

            }
        }
    }

}