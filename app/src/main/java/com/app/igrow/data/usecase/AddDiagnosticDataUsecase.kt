package com.app.igrow.data.usecase

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddDiagnosticDataUsecase @Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(list: ArrayList<Diagnostic>): Flow<DataState<String>> {
        val convertedMap = diagnosticListToMap(list)
        return repository.addDiagnosticData(convertedMap)
    }

    private fun diagnosticListToMap(diagnosticList: ArrayList<Diagnostic>): HashMap<String, Diagnostic> {
        val map = HashMap<String, Diagnostic>()
        try {
            diagnosticList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }
}