package com.app.igrow.data.local.abstraction

import com.app.igrow.data.DataState
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.model.sheets.Diagnostic
import kotlinx.coroutines.flow.Flow

interface DiagnosticRepo {
    suspend fun getAllDiagnostic()  : Flow<DataState<ArrayList<DiagnosticEntityName>>>
    suspend fun insertDiagnostic(dataList: List<DiagnosticEntityName>)
}