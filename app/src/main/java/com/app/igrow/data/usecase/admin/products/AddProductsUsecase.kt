package com.app.igrow.data.usecase.admin.products

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddProductsUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke( productsMap: HashMap<String, Products>): Flow<DataState<String>> {
        return repository.addProductsData(productsMap)
    }
}