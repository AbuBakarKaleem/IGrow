package com.app.igrow.data.local.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.local.abstraction.DistributorsRepo
import com.app.igrow.data.local.dao.DistributorsDao
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DistributorsRepoImpl @Inject constructor(
    private val distributorsDao: DistributorsDao,
    private val stringUtils: StringUtils
) : DistributorsRepo {
    override suspend fun getAllDistributors(): Flow<DataState<ArrayList<DistributorsEntityName>>> = callbackFlow{
        var result = distributorsDao.getAllDistributors()
        if (result.isEmpty()) {
            trySend(DataState.error(stringUtils.noRecordFoundMsg()))
        } else {
            trySend(DataState.success(result as ArrayList<DistributorsEntityName>))
        }
    }

    override suspend fun insertDistributors(dataList: List<DistributorsEntityName>) {
        TODO("Not yet implemented")
    }
}