package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ISColumnValueExistUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(
        columnName: String,
        columnValue: String,
        sheetName: String
    ): Flow<DataState<String>> {
        return repository.isColumnValueExist(
            columnName = columnName,
            columnValue = columnValue,
            sheetName = sheetName
        )
    }
}