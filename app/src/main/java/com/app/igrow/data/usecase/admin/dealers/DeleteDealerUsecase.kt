package com.app.igrow.data.usecase.admin.dealers

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteDealerUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(id: String): Flow<DataState<String>> {
        return repository.deleteDealersData(id)
    }
}