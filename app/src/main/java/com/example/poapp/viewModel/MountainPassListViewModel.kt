package com.example.poapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.poapp.model.AppDatabase
import com.example.poapp.model.entity.*
import com.example.poapp.model.repository.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MountainPassListViewModel(application: Application) : AndroidViewModel(application) {

    private val mountainPassOfficialRepository: MountainPassOfficialRepository
    private val officialPointRepository: OfficialPointRepository
    private val mountainPassUserRepository: MountainPassUserRepository
    private val userPointRepository: UserPointRepository
    private val mountainGroupRepository: MountainGroupRepository
    private val mountainRangeRepository: MountainRangeRepository
    private val routeSectionRepository: RouteSectionRepository
    var routeId = 0
    val routeSection =
        MutableLiveData(RouteSection(0, 0, null, null, 0))

    init {
        val database = AppDatabase.getInstance(application)
        mountainPassOfficialRepository = MountainPassOfficialRepository(database.mountainPassOfficialDAO())
        mountainPassUserRepository = MountainPassUserRepository(database.mountainPassUserDAO())
        officialPointRepository = OfficialPointRepository(database.officialPointDAO())
        mountainGroupRepository = MountainGroupRepository(database.mountainGroupDAO())
        mountainRangeRepository = MountainRangeRepository(database.mountainRangeDAO())
        userPointRepository = UserPointRepository(database.userPointDAO())
        routeSectionRepository = RouteSectionRepository(database.routeSectionDAO())
    }

    fun saveRouteSection() {
        viewModelScope.launch(Dispatchers.IO) {
            routeSection.value!!.FKtrasa = routeId
            routeSectionRepository.insert(routeSection.value!!)
        }
    }

    fun getAllOfficialPasses(): LiveData<List<MountainPassOfficial>> {
        return mountainPassOfficialRepository.getAllActive()
    }

    fun getOfficialPoint(id: Int): OfficialPoint {
        return officialPointRepository.getOfficialPoint(id)[0]
    }

    fun getAllUserPasses(): LiveData<List<MountainPassUser>> {
        return mountainPassUserRepository.getAll()
    }

    fun getUserPoint(id: Int): UserPoint {
        return userPointRepository.getUserPoint(id)[0]
    }

    fun getOfficialPass(id: Int): MountainPassOfficial {
        return mountainPassOfficialRepository.geMountainPass(id)[0]
    }

    fun getUserPass(id: Int): MountainPassUser {
        return mountainPassUserRepository.geMountainPass(id)[0]
    }

}