package com.app.igrow.data.local.abstraction

import com.app.igrow.data.DataState
import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import kotlinx.coroutines.flow.Flow

interface DealersRepo {
    suspend fun getAllDealers()  : Flow<DataState<ArrayList<DealersEntityName>>>
    suspend fun insertDealers(dataList: List<DealersEntityName>)
}