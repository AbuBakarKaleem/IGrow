package com.app.igrow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.igrow.data.local.models.entities.DiagnosticEntityName
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.utils.Constants

@Dao
abstract class DistributorsDao {
    @Query("Select *FROM ${Constants.SHEET_DISTRIBUTORS}")
    abstract fun getAllDistributors(): List<Distributors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDistributors(dataList: List<DistributorsEntityName>)
}