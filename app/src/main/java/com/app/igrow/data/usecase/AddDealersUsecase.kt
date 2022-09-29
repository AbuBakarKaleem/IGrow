package com.app.igrow.data.usecase

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.Utils
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDealersUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        dealersMap: HashMap<String,Dealers>
    ): Flow<DataState<String>> {
        return repository.addDealersData(dealersMap)
    }

    private fun dealersListToMap(dataList: ArrayList<Dealers>): HashMap<String, Dealers> {
        val map = HashMap<String, Dealers>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}