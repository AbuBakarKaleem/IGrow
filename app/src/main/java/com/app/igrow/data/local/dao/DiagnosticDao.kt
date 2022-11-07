package com.app.igrow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.utils.Constants

@Dao
abstract class DiagnosticDao {

    @Query("Select *FROM ${Constants.SHEET_DIAGNOSTIC}")
    abstract fun getAllDiagnostics(): List<DiagnosticEntityName>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDiagnostic(dataList: List<DiagnosticEntityName>)

    @Query("SELECT COUNT(*) FROM ${Constants.SHEET_DIAGNOSTIC}")
    abstract fun getDiagnosticCount(): Int

    @Query("SELECT DISTINCT :columnName FROM ${Constants.SHEET_DIAGNOSTIC}")
    abstract fun getDiagnosticColumnData(columnName: String): List<String>


}