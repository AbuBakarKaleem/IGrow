package com.app.igrow.ui.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.usecase.user.general.GetColumnDataUsecase
import com.app.igrow.data.usecase.user.general.SearchByNameUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosticFragmentViewModel @Inject constructor(
    private val getColumnDataUsecase: GetColumnDataUsecase,
    private val searchByNameUsecase: SearchByNameUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDiagnosticColumnDataMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var getDiagnosticColumnDataLiveData: MutableLiveData<ArrayList<String>?> =
        getDiagnosticColumnDataMutableLiveData

    private var searchByNameMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var searchByNameLiveData: MutableLiveData<ArrayList<String>?> = searchByNameMutableLiveData

    fun getDiagnosticColumnData(columnName: String, sheetName: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getColumnDataUsecase.invoke(columnName = columnName, sheetName = sheetName).collect {
                when (it) {
                    is DataState.Success -> {
                        getDiagnosticColumnDataMutableLiveData.postValue(it.data)
                    }
                    is DataState.Error -> {
                        getDiagnosticColumnDataMutableLiveData.postValue(null)
                    }

                }
                _uiState.postValue(UnloadingState)
            }
        }
    }

    fun searchByName(name: String, sheetName: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            searchByNameUsecase.invoke(name = name, sheetName = sheetName).collect {
                when (it) {
                    is DataState.Success -> {
                        searchByNameMutableLiveData.postValue(it.data)
                    }
                    is DataState.Error -> {
                        searchByNameMutableLiveData.postValue(null)
                    }

                }
                _uiState.postValue(UnloadingState)
            }
        }
    }

}