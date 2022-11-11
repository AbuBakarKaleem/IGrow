package com.app.igrow.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.app.igrow.data.local.models.entities.DistributorsEntityName
import com.app.igrow.utils.Constants

@Dao
abstract class DistributorsDao {
    @Query("Select *FROM ${Constants.SHEET_DISTRIBUTORS}")
    abstract fun getAllDistributors(): List<DistributorsEntityName>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertDistributors(dataList: List<DistributorsEntityName>)

    @Query("SELECT COUNT(*) FROM ${Constants.SHEET_DISTRIBUTORS}")
    abstract fun getDistributorsCount(): Int

    @RawQuery
    abstract fun getDistributorsColumnData(query: SupportSQLiteQuery): List<String>
}