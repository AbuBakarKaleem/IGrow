package com.app.igrow.ui.admin.diagnostic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.usecase.admin.diagnostic.DeleteDiagnosticUsecase
import com.app.igrow.data.usecase.admin.diagnostic.GetDiagnosticUsecase
import com.app.igrow.data.usecase.admin.diagnostic.UpdateDiagnosticUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosticActivityViewModel @Inject constructor(
    private val getDiagnosticUsecase: GetDiagnosticUsecase,
    private val updateDiagnosticUsecase: UpdateDiagnosticUsecase,
    private val deleteDiagnosticUsecase: DeleteDiagnosticUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDiagnosticMutableLiveData = MutableLiveData<Any?>()
    var getDiagnosticLiveData: MutableLiveData<Any?> = getDiagnosticMutableLiveData

    private var updateDiagnosticMutableLiveData = MutableLiveData<String>()
    var updateDiagnosticLiveData: MutableLiveData<String> = updateDiagnosticMutableLiveData

    private var  deleteDiagnosticMutableLiveData = MutableLiveData<String>()
    var  deleteDiagnosticLiveData: MutableLiveData<String> =  deleteDiagnosticMutableLiveData

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
    fun deleteDiagnostic(id: String,map:HashMap<String,Diagnostic>){
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            deleteDiagnosticUsecase.invoke(id,map).collect {
                when (it) {
                    is DataState.Success -> {
                        deleteDiagnosticMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        deleteDiagnosticMutableLiveData.postValue(it.message)
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