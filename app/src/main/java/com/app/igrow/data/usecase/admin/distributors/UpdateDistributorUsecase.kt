package com.app.igrow.data.usecase.admin.distributors

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDistributorUsecase@Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(distributors: HashMap<String,Distributors>): Flow<DataState<String>> {
        return repository.updateDistributorsData(distributors)
    }

}