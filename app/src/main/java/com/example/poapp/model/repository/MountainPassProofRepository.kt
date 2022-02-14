package com.example.poapp.model.repository

import com.example.poapp.model.dao.MountainPassProofDAO
import com.example.poapp.model.entity.MountainPassProof
import com.example.poapp.model.entity.RouteSection

class MountainPassProofRepository(private val mountainPassProofDAO: MountainPassProofDAO) {

    fun proofsFor(routeSectionId: Long): List<MountainPassProof> {
        return mountainPassProofDAO.proofsFor(routeSectionId)
    }

    fun proofsFor(routeSections: List<RouteSection>): List<MountainPassProof> {
        val routeSectionsIds = mutableListOf<Long>()
        for (r in routeSections)
            routeSectionsIds.add(r.id.toLong())
        return mountainPassProofDAO.proofsFor(routeSectionsIds)
    }

    fun insert(mountainPassProof: MountainPassProof) {
        mountainPassProofDAO.insert(mountainPassProof)
    }

    fun routeSectionsIDsFor(proofId: Long): List<Int> {
        val mountainPassProofs = mountainPassProofDAO.sectionsFor(proofId.toInt())
        val sectionsIDs = mutableListOf<Int>()
        for (passProof in mountainPassProofs) {
            sectionsIDs.add(passProof.FKodcinek)
        }
        return sectionsIDs
    }

    fun deleteAllForProof(proofId: Int) {
        mountainPassProofDAO.deleteAllForProof(proofId)
    }

    fun delete(mountainPassProofID: Int) {
        mountainPassProofDAO.delete(mountainPassProofID)
    }
}