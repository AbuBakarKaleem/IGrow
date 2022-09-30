package com.app.igrow.data.usecase.admin.dealers

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDealersUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        dealersMap: HashMap<String,Dealers>
    ): Flow<DataState<String>> {
        return repository.addDealersData(dealersMap)
    }
}