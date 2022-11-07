package com.app.igrow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.igrow.data.local.models.entities.DealersEntityName
import com.app.igrow.utils.Constants

@Dao
abstract class DealersDao {
    @Query("Select *FROM ${Constants.SHEET_DEALERS}")
    abstract fun getAllDealers(): List<DealersEntityName>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDealers(dataList: List<DealersEntityName>)

    @Query("SELECT COUNT(*) FROM ${Constants.SHEET_DEALERS}")
    abstract fun getDealerCount(): Int

    @Query("SELECT DISTINCT :columnName FROM ${Constants.SHEET_DEALERS}")
    abstract fun getDealersColumnData(columnName: String): List<String>
}