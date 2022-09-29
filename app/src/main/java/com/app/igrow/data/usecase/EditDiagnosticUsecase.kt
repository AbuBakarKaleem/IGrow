package com.app.igrow.data.usecase

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EditDiagnosticUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(id: String): Flow<DataState<Any>> {
        return repository.getDiagnosticData(id)
    }
}