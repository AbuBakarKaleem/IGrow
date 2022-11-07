package com.app.igrow.data.local.abstraction

import com.app.igrow.data.DataState
import com.app.igrow.data.local.models.entities.ProductsEntityName
import kotlinx.coroutines.flow.Flow

interface ProductsRepo {
    suspend fun getAllProducts(): ArrayList<ProductsEntityName>
    suspend fun insertProducts(dataList: List<ProductsEntityName>)
    suspend fun getProductsCount():Int
    suspend fun getProductsColumnData(columnName: String):List<String>
}