package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDistributorByNameUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(name: String, columnName: String): Flow<DataState<Distributors>> {
        return repository.getDistributorDataByName(name = name, columnName = columnName)
    }
}