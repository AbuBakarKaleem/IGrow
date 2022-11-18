package com.app.igrow.data.usecase.admin.diagnostic

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.Constants
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FilterDataListOfGivenSheetUseCase @Inject constructor(
    private val repository: Repository,
    private val stringUtils: StringUtils
) {

    suspend operator fun invoke(sheetName:String, filters: HashMap<String, String>): Flow<DataState<ArrayList<HashMap<String, String>>>> =
        callbackFlow {

            val dataList = repository.getAllDataOfGivenSheet(sheetName = sheetName)
            dataList.collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        dataState.data?.let {
                            val result = filterDiagnosticList(
                                filters = filters,
                                dataHashMap = it,
                                sheetName = sheetName
                            )
                            trySend(DataState.Success(result))
                        } ?: run {
                            trySend(DataState.Error(stringUtils.noRecordFoundMsg()))
                        }
                    }
                    is DataState.Error -> {
                        trySend(DataState.Error(stringUtils.noRecordFoundMsg()))
                    }
                }
            }

        }

    private fun filterDiagnosticList(
        filters: HashMap<String, String>,
        dataHashMap: ArrayList<HashMap<String, String>>,
        sheetName: String
    ): ArrayList<HashMap<String, String>> {

        if (filters.isEmpty()) {
            return dataHashMap
        }

        val list = ArrayList<HashMap<String, String>>()
        val localHashMap = ArrayList<HashMap<String, String>>()

        if (localHashMap.isEmpty()) {
            localHashMap.addAll(dataHashMap)
        }

        filters.forEach { filterKey ->
            localHashMap.forEach { data ->
                if ( sheetName == Constants.SHEET_DEALERS ) {
                    if ( filterKey.key == Constants.COL_DISTRIBUTORS_NAME &&
                        filters.contains(Constants.COL_DISTRIBUTORS_NAME) ) {
                        val result = data[Constants.COL_DISTRIBUTORS]?:""
                        if ( result.contains(filterKey.value) ) {
                            list.add(data)
                        }
                    } else {
                        if ( filterKey.value == data[filterKey.key] ) {
                            list.add(data)
                        }
                    }
                } else {
                    if (filterKey.value == data[filterKey.key]) {
                        list.add(data)
                    }
                }
            }

            localHashMap.clear()
            localHashMap.addAll(list)
            list.clear()
        }

        return localHashMap


    }

}