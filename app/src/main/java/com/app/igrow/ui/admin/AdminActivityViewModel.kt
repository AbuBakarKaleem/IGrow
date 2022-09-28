package com.app.igrow.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.usecase.AddDealersUsecase
import com.app.igrow.data.usecase.AddDiagnosticDataUsecase
import com.app.igrow.data.usecase.AddDistributorsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminActivityViewModel @Inject constructor(
    private val addDiagnosticUseCase: AddDiagnosticDataUsecase,
    private val addDistributorsUsecase: AddDistributorsUsecase,
    private val addDealersUsecase: AddDealersUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var addDiagnosticMutableLiveData = MutableLiveData<String?>()
    var addDiagnosticLiveData: MutableLiveData<String?> = addDiagnosticMutableLiveData

    fun addDiagnosticData(diagnosticList: ArrayList<Diagnostic>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            addDiagnosticUseCase.invoke(diagnosticList).collect {
                when (it) {
                    is DataState.Success -> {
                        addDiagnosticMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        addDiagnosticMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                    else -> {
                        println("In==> else")
                    }
                }
            }
        }
    }
}