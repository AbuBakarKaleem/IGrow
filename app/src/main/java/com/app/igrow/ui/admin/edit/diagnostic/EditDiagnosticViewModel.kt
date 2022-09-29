package com.app.igrow.ui.admin.edit.diagnostic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.usecase.GetDiagnosticUsecase
import com.app.igrow.data.usecase.UpdateDiagnosticUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDiagnosticViewModel @Inject constructor(
    private val getDiagnosticUsecase: GetDiagnosticUsecase,
    private val updateDiagnosticUsecase: UpdateDiagnosticUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDiagnosticMutableLiveData = MutableLiveData<Any?>()
    var getDiagnosticLiveData: MutableLiveData<Any?> = getDiagnosticMutableLiveData

    private var updateDiagnosticMutableLiveData = MutableLiveData<String>()
    var updateDiagnosticLiveData: MutableLiveData<String> = updateDiagnosticMutableLiveData

    fun getDiagnostic(id:String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getDiagnosticUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        getDiagnosticMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        getDiagnosticMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
    fun updateDiagnostic(updatedDiagnostic: HashMap<String,Diagnostic>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            updateDiagnosticUsecase.invoke(updatedDiagnostic).collect {
                when (it) {
                    is DataState.Success -> {
                        updateDiagnosticMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        updateDiagnosticMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
}