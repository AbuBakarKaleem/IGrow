package com.app.igrow.data.usecase.admin.products

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: String): Flow<DataState<Any>> {
        return repository.getProductsData(id)
    }
}