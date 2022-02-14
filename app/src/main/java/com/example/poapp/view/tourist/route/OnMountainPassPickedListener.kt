package com.example.poapp.view.tourist.route

import com.example.poapp.model.entity.MountainPassOfficial
import com.example.poapp.model.entity.MountainPassUser

interface OnMountainPassPickedListener {
    fun onPassSelected(official: MountainPassOfficial?, user: MountainPassUser?)
}