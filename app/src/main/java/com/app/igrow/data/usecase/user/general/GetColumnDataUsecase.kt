package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetColumnDataUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(columnName: String,sheetName:String): Flow<DataState<ArrayList<String>>> {
        return repository.getColumnData(columnName = columnName, sheetName = sheetName)
    }
}