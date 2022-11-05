package com.app.igrow.data.local.repository

import android.util.Log
import com.app.igrow.data.local.abstraction.DealersRepo
import com.app.igrow.data.local.dao.DealersDao
import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.utils.StringUtils
import javax.inject.Inject

class DealersRepoImpl @Inject constructor(
    private val dealersDao: DealersDao,
    private val stringUtils: StringUtils
) : DealersRepo {

    override suspend fun getAllDealers(): ArrayList<DealersEntityName> {
        val result = dealersDao.getAllDealers()
        return if (result.isEmpty()) {
            arrayListOf()
        } else {
            (result as ArrayList<DealersEntityName>)
        }
    }


    override suspend fun insertDealers(dataList: List<DealersEntityName>) {
        try {
            dealersDao.insertDealers(dataList)
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }
    }

    companion object {
        const val TAG = "DealersRepoImpl"
    }

}