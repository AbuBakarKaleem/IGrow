package com.app.igrow.data.usecase.admin.diagnostic

import com.app.igrow.data.DataState
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FilterDiagnosticsListUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(filters: HashMap<String, String>): Flow<DataState<Any>> =
        callbackFlow {
            val diagnosticsList = repository.getAllDiagnosticData()

            diagnosticsList.collect { dataState ->
                when (dataState) {
                    is DataState.Success -> {
                        dataState.data?.let {
                            filterDiagnosticList(
                                filters = filters,
                                dataHashMap = it
                            )
                        }
                    }
                    is DataState.Error -> {

                    }
                }
            }

        }

    private fun filterDiagnosticList(
        filters: HashMap<String, String>,
        dataHashMap: ArrayList<HashMap<String, String>>
    ) {

        val list = ArrayList<HashMap<String, String>>()
        var localHashMap = ArrayList<HashMap<String, String>>()

        if(localHashMap.isEmpty()){
            localHashMap.addAll(dataHashMap)
        }

        filters.forEach { filterKey ->
            println("=-=>> before size is: ${localHashMap.size} , filtered list: ${list.size}")
            localHashMap.forEach { data ->
                if (filterKey.value == data[filterKey.key]) {
                    list.add(data)
                }
            }
            println("=-=>> after size is: ${localHashMap.size} , filtered list: ${list.size}")
            localHashMap.clear()
            localHashMap.addAll(list)
            list.clear()

            // butt end result list localHashMap ma mile gi tje.
        }

        println("=-=>> result is: ${list.size} , ${localHashMap.size}")


    }

}