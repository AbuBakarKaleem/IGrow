package com.app.igrow.data.local.abstraction

import com.app.igrow.data.local.models.entities.ProductsEntityName

interface ProductsRepo {
    suspend fun getAllProducts(): ArrayList<ProductsEntityName>
    suspend fun insertProducts(dataList: List<ProductsEntityName>)
    suspend fun getProductsCount(): Int
    suspend fun getProductsColumnData(filtersMap: HashMap<String, String>,sheetName: String, columnName: String): List<String>
    suspend fun isColumnValueExist(columnName: String,columnValue: String,sheetName: String): String
}