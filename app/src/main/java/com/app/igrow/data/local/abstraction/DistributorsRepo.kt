package com.app.igrow.data.local.abstraction

import com.app.igrow.data.DataState
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import kotlinx.coroutines.flow.Flow

interface DistributorsRepo {

    suspend fun getAllDistributors(): Flow<DataState<ArrayList<DistributorsEntityName>>>
    suspend fun insertDistributors(dataList: List<DistributorsEntityName>)
}