package com.app.igrow.data.local.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.app.igrow.data.local.models.entities.ProductsEntityName
import com.app.igrow.utils.Constants

@Dao
abstract class ProductsDao {
    @Query("Select *FROM ${Constants.SHEET_PRODUCTS}")
    abstract fun getAllProducts(): List<ProductsEntityName>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProducts(dataList: List<ProductsEntityName>)

    @Query("SELECT COUNT(*) FROM ${Constants.SHEET_PRODUCTS}")
    abstract fun getProductsCount(): Int

    @RawQuery
    abstract fun getProductsColumnData(query: SupportSQLiteQuery): List<String>

    @RawQuery
    abstract fun isColumnValueExist(query: SupportSQLiteQuery): String
}