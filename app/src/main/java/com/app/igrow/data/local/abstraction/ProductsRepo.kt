package com.app.igrow.data.local.abstraction

import com.app.igrow.data.local.models.entities.ProductsEntityName

interface ProductsRepo {
    suspend fun getAllProducts(): ArrayList<ProductsEntityName>
    suspend fun insertProducts(dataList: List<ProductsEntityName>)
    suspend fun getProductsCount(): Int
    suspend fun getProductsColumnData(sheetName: String, columnName: String): List<String>
}