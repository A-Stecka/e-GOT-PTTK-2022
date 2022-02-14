package com.example.poapp.model.repository

import com.example.poapp.model.dao.ProofDAO
import com.example.poapp.model.entity.Proof

class ProofRepository(private val proofDAO: ProofDAO) {

    fun getProof(proofID: Long): Proof {
        return proofDAO.getProof(proofID)
    }

    fun insert(proof: Proof): Long {
        return proofDAO.insert(proof)
    }

    fun delete(proofID: Long) {
        proofDAO.delete(proofID)
    }
}