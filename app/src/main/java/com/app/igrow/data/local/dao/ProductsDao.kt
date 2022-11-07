package com.app.igrow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("SELECT DISTINCT :columnName FROM ${Constants.SHEET_PRODUCTS}")
    abstract fun getProductsColumnData(columnName: String): List<String>
}