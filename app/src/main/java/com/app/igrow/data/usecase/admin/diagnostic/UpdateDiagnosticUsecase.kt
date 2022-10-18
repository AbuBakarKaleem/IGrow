package com.app.igrow.data.usecase.admin.diagnostic

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateDiagnosticUsecase@Inject constructor(private val repository: Repository) {

    suspend operator fun invoke(diagnostic: HashMap<String,Diagnostic>): Flow<DataState<String>> {
        return repository.updateDiagnostic(diagnostic)
    }

}