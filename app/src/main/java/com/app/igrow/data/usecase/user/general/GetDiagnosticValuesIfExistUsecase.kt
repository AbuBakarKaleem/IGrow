package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.Constants.COL_CROP
import com.app.igrow.utils.Constants.COL_ENEMY
import com.app.igrow.utils.Constants.COL_TYPE_OF_ENEMY
import com.app.igrow.utils.Utils
import com.app.igrow.utils.Utils.getLocalizeColumnName
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetDiagnosticValuesIfExistUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(
        diagnostic: Diagnostic,
        sheetName: String,
    ): Flow<DataState<HashMap<String, String>>> {
        val resultHashMap = HashMap<String, String>()
        runBlocking {
            val coroutineScope = CoroutineScope(Dispatchers.IO)
            val tasks = listOf(
                coroutineScope.async {
                    val value =
                        if (Utils.isLocaleFrench()) diagnostic.crop_fr else diagnostic.crop
                    val columnName = getLocalizeColumnName(COL_CROP)
                    repository.isColumnValueExist(columnName, value, sheetName)
                        .collect { dataState ->
                            getValue(dataState)?.let {
                                resultHashMap[columnName] = it
                            }
                        }
                },
                coroutineScope.async {
                    val value =
                        if (Utils.isLocaleFrench()) diagnostic.type_of_enemy_fr else diagnostic.type_of_enemy
                    val columnName = getLocalizeColumnName(COL_TYPE_OF_ENEMY)
                    repository.isColumnValueExist(columnName, value, sheetName)
                        .collect { dataState ->
                            getValue(dataState)?.let {
                                resultHashMap[columnName] = it
                            }
                        }
                },
                coroutineScope.async {
                    val value =
                        if (Utils.isLocaleFrench()) diagnostic.causal_agent_fr else diagnostic.causal_agent
                    val columnName = getLocalizeColumnName(COL_ENEMY)
                    repository.isColumnValueExist(columnName, value, sheetName)
                        .collect { dataState ->
                            getValue(dataState)?.let {
                                resultHashMap[columnName] = it
                            }
                        }
                }
            )
            awaitAll(*tasks.toTypedArray())
            return@runBlocking resultHashMap
        }
        return flowOf(DataState.Success(resultHashMap))
    }


    fun getValue(state: DataState<String>): String? {
        when (state) {
            is DataState.Success -> {
                val data = state.data?.split("-") ?: return ""
                if (data[2].isNotEmpty() && data[2] != "null") {
                    return data[1]
                }
            }
            is DataState.Error -> {
                return null
            }
        }
        return null
    }

}