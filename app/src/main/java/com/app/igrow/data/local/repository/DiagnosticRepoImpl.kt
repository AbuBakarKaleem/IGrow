package com.app.igrow.data.local.repository

import android.util.Log
import com.app.igrow.data.local.abstraction.DiagnosticRepo
import com.app.igrow.data.local.dao.DiagnosticDao
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.utils.StringUtils
import javax.inject.Inject

class DiagnosticRepoImpl @Inject constructor(
    private val diagnosticDao: DiagnosticDao,
    private val stringUtils: StringUtils,
) : DiagnosticRepo {
    override suspend fun getAllDiagnostic(): ArrayList<DiagnosticEntityName> {
        val result = diagnosticDao.getAllDiagnostics()
        return if (result.isEmpty()) {
            arrayListOf()
        } else {
            (result as ArrayList<DiagnosticEntityName>)
        }
    }

    override suspend fun insertDiagnostic(dataList: List<DiagnosticEntityName>) {
        try {
            diagnosticDao.insertDiagnostic(dataList)
        } catch (e: Exception) {
            Log.e(TAG, e.printStackTrace().toString())
        }

    }

    override suspend fun getDiagnosticCount(): Int {
        return diagnosticDao.getDiagnosticCount()
    }

    override suspend fun getDiagnosticColumnData(columnName: String): List<String> {
        return diagnosticDao.getDiagnosticColumnData(columnName = columnName)
    }

    companion object {
        const val TAG = "DiagnosticRepoImpl"
    }
}