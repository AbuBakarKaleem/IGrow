package com.app.igrow.data.local.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.local.abstraction.DealersRepo
import com.app.igrow.data.local.dao.DealersDao
import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DealersRepoImpl @Inject constructor(
    private val dealersDao: DealersDao,
    private val stringUtils: StringUtils
) : DealersRepo {
    override suspend fun getAllDealers(): Flow<DataState<ArrayList<DealersEntityName>>> =
        callbackFlow {
            var result = dealersDao.getAllDealers()
            if (result.isEmpty()) {
                trySend(DataState.error(stringUtils.noRecordFoundMsg()))
            } else {
                result
                trySend(DataState.success(result as ArrayList<DealersEntityName>))
            }
        }

    override suspend fun insertDealers(dataList: List<DealersEntityName>) {
        TODO("Not yet implemented")
    }

}