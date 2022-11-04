package com.app.igrow.data.local.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.local.abstraction.ProductsRepo
import com.app.igrow.data.local.dao.ProductsDao
import com.app.igrow.data.local.models.entities.ProductsEntityName
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val stringUtils: StringUtils
) :
    ProductsRepo {
    override suspend fun getAllProducts(): Flow<DataState<ArrayList<ProductsEntityName>>> =
        callbackFlow {
            var result = productsDao.getAllProducts()
            if (result.isEmpty()) {
                trySend(DataState.error(stringUtils.noRecordFoundMsg()))
            } else {
                trySend(DataState.success(result as ArrayList<ProductsEntityName>))
            }
        }

    override suspend fun insertProducts(dataList: List<ProductsEntityName>) {
        TODO("Not yet implemented")
    }
}