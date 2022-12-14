package com.app.igrow.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
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

    @RawQuery
    abstract fun getDealersColumnData(query: SupportSQLiteQuery): List<String>
}