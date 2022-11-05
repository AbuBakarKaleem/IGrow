package com.app.igrow.data.local.repository

import android.util.Log
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
    override suspend fun getAllDistributors():ArrayList<DistributorsEntityName> {
        val result = distributorsDao.getAllDistributors()
        return if (result.isEmpty()) {
            arrayListOf()
        } else {
            (result as ArrayList<DistributorsEntityName>)
        }
    }

    override suspend fun insertDistributors(dataList: List<DistributorsEntityName>) {
        try {
            distributorsDao.insertDistributors(dataList)
        } catch (e: Exception) {
            Log.e(DiagnosticRepoImpl.TAG, e.printStackTrace().toString())
        }    }

    companion object {
        const val TAG = "DistributorsRepoImpl"
    }

}