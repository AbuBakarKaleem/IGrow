package com.app.igrow.data.usecase

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDistributorsUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        distributorsMap: HashMap<String, Distributors>
    ) : Flow<DataState<String>> {
        return repository.addDistributorsData(distributorsMap)
    }
}