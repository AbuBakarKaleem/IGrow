package com.app.igrow.data.local.repository

import android.util.Log
import com.app.igrow.data.local.abstraction.DistributorsRepo
import com.app.igrow.data.local.dao.DistributorsDao
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.utils.StringUtils
import com.app.igrow.utils.Utils
import javax.inject.Inject

class DistributorsRepoImpl @Inject constructor(
    private val distributorsDao: DistributorsDao,
    private val stringUtils: StringUtils
) : DistributorsRepo {
    override suspend fun getAllDistributors(): ArrayList<DistributorsEntityName> {
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
        }
    }

    override suspend fun getDistributorsCount(): Int {
        return distributorsDao.getDistributorsCount()
    }

    override suspend fun getDistributorsColumnData(
        sheetName: String,
        columnName: String
    ): List<String> {
        return distributorsDao.getDistributorsColumnData(
            Utils.getColumnDataCustomQuery(
                sheetName = sheetName,
                columnName = columnName
            )
        )
    }

    override suspend fun getDistributorByName(
        name: String,
        columnName: String
    ): DistributorsEntityName {
        if (name.isNotEmpty() && columnName.isNotEmpty()) {
            val query =  Utils.getDistributorByName(name, columnName)
            val data  = distributorsDao.getDistributorByName(query)
            return data
        }
        return DistributorsEntityName()
    }

    companion object {
        const val TAG = "DistributorsRepoImpl"
    }

}