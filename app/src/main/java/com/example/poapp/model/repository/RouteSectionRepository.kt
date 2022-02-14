package com.example.poapp.model.repository

import com.example.poapp.model.dao.RouteSectionDAO
import com.example.poapp.model.entity.RouteSection

class RouteSectionRepository(private val routeSectionDAO: RouteSectionDAO) {

    fun insert(routeSection: RouteSection): Long {
        return routeSectionDAO.insert(routeSection)
    }

    fun getRouteSectionForRoute(routeID: Int): List<RouteSection> {
        return routeSectionDAO.getRouteSectionForRoute(routeID)
    }

    fun deleteAllFor(routeID: Long) {
        routeSectionDAO.deleteAllFor(routeID)
    }

    fun getSections(sectionsIDs: List<Int>): List<RouteSection> {
        return routeSectionDAO.getSections(sectionsIDs)
    }

}