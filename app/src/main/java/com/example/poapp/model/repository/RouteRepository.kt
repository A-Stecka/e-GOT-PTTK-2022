package com.example.poapp.model.repository

import androidx.lifecycle.LiveData
import com.example.poapp.model.dao.RouteDAO
import com.example.poapp.model.entity.Route

class RouteRepository(private val routeDAO: RouteDAO) {

    fun insert(route: Route): Long {
        return routeDAO.insert(route)
    }

    fun getAllForUser(idUser: Int): LiveData<List<Route>> {
        return routeDAO.getAll(idUser)
    }

    fun getAll(): List<Route> {
        return routeDAO.getAll()
    }

    fun getRoute(id: Int): List<Route> {
        return routeDAO.getRoute(id)
    }

    fun update(route: Route) {
        routeDAO.update(route)
    }

    fun delete(routeId: Long) {
        routeDAO.delete(routeId)
    }

    fun getForConfirmation(): List<Route> {
        return routeDAO.getAllWithStatus("oczekuje na potwierdzenie")
    }
}