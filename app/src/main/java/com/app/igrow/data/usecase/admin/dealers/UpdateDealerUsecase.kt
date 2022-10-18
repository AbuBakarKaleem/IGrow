package com.app.igrow.data.usecase.admin.dealers

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDealerUsecase@Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(dealer: HashMap<String,Dealers>): Flow<DataState<String>> {
        return repository.updateDealersData(dealer)
    }

}