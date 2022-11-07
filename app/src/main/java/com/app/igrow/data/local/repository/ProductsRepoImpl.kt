package com.app.igrow.data.local.repository

import android.util.Log
import com.app.igrow.data.local.abstraction.ProductsRepo
import com.app.igrow.data.local.dao.ProductsDao
import com.app.igrow.data.local.models.entities.ProductsEntityName
import com.app.igrow.utils.StringUtils
import com.app.igrow.utils.Utils
import javax.inject.Inject

class ProductsRepoImpl @Inject constructor(
    private val productsDao: ProductsDao,
    private val stringUtils: StringUtils
) :
    ProductsRepo {
    override suspend fun getAllProducts(): ArrayList<ProductsEntityName> {
        val result = productsDao.getAllProducts()
        return if (result.isEmpty()) {
            arrayListOf()
        } else {
            (result as ArrayList<ProductsEntityName>)
        }
    }


    override suspend fun insertProducts(dataList: List<ProductsEntityName>) {
        try {
            productsDao.insertProducts(dataList)
        } catch (e: Exception) {
            Log.e(DiagnosticRepoImpl.TAG, e.printStackTrace().toString())
        }
    }

    override suspend fun getProductsCount(): Int {
        return productsDao.getProductsCount()
    }

    override suspend fun getProductsColumnData(
        sheetName: String,
        columnName: String
    ): List<String> {
        return productsDao.getProductsColumnData(
            Utils.getColumnDataCustomQuery(
                sheetName = sheetName,
                columnName = columnName
            )
        )
    }
}