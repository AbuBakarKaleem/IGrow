package com.app.igrow.data.usecase

import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.repository.Repository
import javax.inject.Inject

class AddDealersUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        list: ArrayList<Dealers>
    ) = repository.addDealersData(list)
}