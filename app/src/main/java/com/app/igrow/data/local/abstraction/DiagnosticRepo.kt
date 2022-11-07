package com.app.igrow.data.local.abstraction

import com.app.igrow.data.local.models.entities.DiagnosticEntityName

interface DiagnosticRepo {
    suspend fun getAllDiagnostic(): ArrayList<DiagnosticEntityName>
    suspend fun insertDiagnostic(dataList: List<DiagnosticEntityName>)
    suspend fun getDiagnosticCount(): Int
    suspend fun getDiagnosticColumnData(sheetName: String, columnName: String): List<String>
}