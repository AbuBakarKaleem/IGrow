package com.app.igrow.data.local.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.local.abstraction.DiagnosticRepo
import com.app.igrow.data.local.dao.DiagnosticDao
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DiagnosticRepoImpl @Inject constructor(
    private val diagnosticDao: DiagnosticDao,
    private val stringUtils: StringUtils
) : DiagnosticRepo {
    override suspend fun getAllDiagnostic(): Flow<DataState<ArrayList<DiagnosticEntityName>>> = callbackFlow {
        var result = diagnosticDao.getAllDiagnostics()
        if (result.isEmpty()) {
            trySend(DataState.error(stringUtils.noRecordFoundMsg()))
        } else {
            trySend(DataState.success(result as ArrayList<DiagnosticEntityName>))
        }
    }

    override suspend fun insertDiagnostic(dataList: List<DiagnosticEntityName>)  {
        TODO("Not yet implemented")
    }
}