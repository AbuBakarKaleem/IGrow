package com.app.igrow.data.usecase

import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.repository.Repository
import javax.inject.Inject

class AddDistributorsUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        list: ArrayList<Distributors>
    ) = repository.addDistributorsData(list)
}