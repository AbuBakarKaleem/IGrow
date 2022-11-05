package com.app.igrow.data.local.abstraction

import com.app.igrow.data.local.models.entities.DealersEntityName

interface DealersRepo {
    suspend fun getAllDealers(): ArrayList<DealersEntityName>
    suspend fun insertDealers(dataList: List<DealersEntityName>)
}