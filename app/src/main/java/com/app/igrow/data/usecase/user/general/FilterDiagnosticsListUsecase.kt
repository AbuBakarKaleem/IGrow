package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.Constants
import com.app.igrow.utils.StringUtils
import com.app.igrow.utils.Utils.getLocalizeColumnName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FilterDataListOfGivenSheetUseCase @Inject constructor(
    private val repository: Repository,
    private val stringUtils: StringUtils,
) {

    suspend operator fun invoke(
        sheetName: String,
        filters: HashMap<String, String>,
    ): Flow<DataState<ArrayList<HashMap<String, String>>>> =
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
        sheetName: String,
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
                if (sheetName == Constants.SHEET_DEALERS) {
                    if (filterKey.key == getLocalizeColumnName(Constants.COL_DISTRIBUTORS_NAME) &&
                        filters.contains(getLocalizeColumnName(Constants.COL_DISTRIBUTORS_NAME))
                    ) {
                        val result = data[getLocalizeColumnName(Constants.COL_DISTRIBUTORS)] ?: ""
                        if (result.contains(filterKey.value) || filterKey.value.contains(result)) {
                            list.add(data)
                        }
                    } else if (filterKey.key == getLocalizeColumnName(Constants.COL_DEALER_NAME) &&
                        filters.contains(getLocalizeColumnName(Constants.COL_DEALER_NAME))
                    ) {
                        val result = data[getLocalizeColumnName(Constants.COL_DEALER_NAME)] ?: ""
                        if (result.contains(filterKey.value, ignoreCase = true) ||
                            filterKey.value.contains(result, ignoreCase = true)) {
                            list.add(data)
                        }
                    } else {
                        val result = data[filterKey.key] ?: ""
                        if (filterKey.value.contains(result, ignoreCase = true) || result.contains(filterKey.value, ignoreCase = true)) {
                            list.add(data)
                        }
                    }
                }
                else if (sheetName == Constants.SHEET_PRODUCTS)
                {
                    if (filterKey.key == getLocalizeColumnName(Constants.COL_PRODUCT_NAME) &&
                        filters.contains(getLocalizeColumnName(Constants.COL_PRODUCT_NAME))
                    ) {

                        val result = data[getLocalizeColumnName(Constants.COL_PRODUCT_NAME)] ?: ""
                        if (result.contains(filterKey.value , ignoreCase = true) ||
                            filterKey.value.contains(result , ignoreCase = true)  ) {
                            list.add(data)
                        }

                    } else {
                        if ((data[filterKey.key]?.let { filterKey.value.contains(it, ignoreCase = true) } == true) ||
                            (data[filterKey.key]?.let { it.contains(filterKey.value, ignoreCase = true) } == true)) {
                            list.add(data)
                        }
                    }
                }
                else if (sheetName == Constants.SHEET_DIAGNOSTIC) {
                    if ( filterKey.key == getLocalizeColumnName(Constants.COL_PLANT_HEALTH_PROBLEM)
                        && (filters.contains(getLocalizeColumnName(Constants.COL_PLANT_HEALTH_PROBLEM)))
                    ) {

                        val result = data[getLocalizeColumnName(Constants.COL_PLANT_HEALTH_PROBLEM)] ?: ""
                        if (result.contains(filterKey.value , ignoreCase = true) ||
                            filterKey.value.contains(result , ignoreCase = true)  ) {
                            list.add(data)
                        }

                    } else {
                        if ((data[filterKey.key]?.let { filterKey.value.contains(it, ignoreCase = true) } == true) ||
                            (data[filterKey.key]?.let { it.contains(filterKey.value, ignoreCase = true) } == true)) {
                            list.add(data)
                        }
                    }
                } else {
                    if ((data[filterKey.key]?.let { filterKey.value.contains(it, ignoreCase = true) } == true) ||
                        (data[filterKey.key]?.let { it.contains(filterKey.value, ignoreCase = true) } == true)) {
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