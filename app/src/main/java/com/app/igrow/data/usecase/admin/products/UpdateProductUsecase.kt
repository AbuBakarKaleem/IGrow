package com.app.igrow.data.usecase.admin.products

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProductUsecase@Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(products: HashMap<String,Products>): Flow<DataState<String>> {
        return repository.updateProductsData(products)
    }

}