package com.app.igrow.ui.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.usecase.admin.diagnostic.FilterDiagnosticsListUsecase
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
    private val searchByNameUsecase: SearchByNameUsecase,
    private val filterDiagnosticsListUsecase: FilterDiagnosticsListUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDiagnosticColumnDataMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var getDiagnosticColumnDataLiveData: MutableLiveData<ArrayList<String>?> =
        getDiagnosticColumnDataMutableLiveData

    private var filterResultMutableLiveData = MutableLiveData<ArrayList<HashMap<String, String>>>()
    var filtersLiveData: MutableLiveData<ArrayList<HashMap<String, String>>> =
        filterResultMutableLiveData

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

    fun searchDiagnostic(filtersMap: HashMap<String, String>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            filterDiagnosticsListUsecase.invoke(filters = filtersMap).collect {
                _uiState.postValue(UnloadingState)

                when (it) {
                    is DataState.Success -> {
                        it.data?.let { response ->
                            filterResultMutableLiveData.postValue(response)
                        }
                    }
                    is DataState.Error -> {
                        filterResultMutableLiveData.postValue(arrayListOf<HashMap<String, String>>())
                    }
                }
            }
        }
    }

}