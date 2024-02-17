package com.app.igrow.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.usecase.user.general.FilterDataListOfGivenSheetUseCase
import com.app.igrow.data.usecase.user.general.GetColumnDataUsecase
import com.app.igrow.data.usecase.user.general.GetDiagnosticValuesIfExistUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getColumnDataUsecase: GetColumnDataUsecase,
    private val filterDataListOfGivenSheetUseCase: FilterDataListOfGivenSheetUseCase,
    private val getDiagnosticValuesIfExistUseCase: GetDiagnosticValuesIfExistUsecase
) : ViewModel() {
    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getProductColumnDataMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var getProductColumnDataLiveData: MutableLiveData<ArrayList<String>?> =
        getProductColumnDataMutableLiveData

    private var filterResultMutableLiveData = MutableLiveData<ArrayList<HashMap<String, String>>>()
    var filtersLiveData: MutableLiveData<ArrayList<HashMap<String, String>>> =
        filterResultMutableLiveData

    private var columnDataExistMutableLiveData = MutableLiveData<HashMap<String,String>>()
    var columnDataExistLiveData: MutableLiveData<HashMap<String,String>> =
        columnDataExistMutableLiveData

    private var showEmptyResponseMsg = false

    fun getProductColumnData(
        filtersMap: HashMap<String, String>,
        columnName: String,
        sheetName: String
    ) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getColumnDataUsecase.invoke(
                filtersMap = filtersMap,
                columnName = columnName,
                sheetName = sheetName
            ).collect {
                when (it) {
                    is DataState.Success -> {
                        getProductColumnDataMutableLiveData.postValue(it.data)
                    }
                    is DataState.Error -> {
                        getProductColumnDataMutableLiveData.postValue(null)
                    }

                }
                _uiState.postValue(UnloadingState)
            }
        }
    }

    fun getAllDiagnosticDataAtOnce(diagnostic: Diagnostic,sheetName: String){
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getDiagnosticValuesIfExistUseCase(diagnostic,sheetName).collect{
                when (it) {
                    is DataState.Success -> {
                        columnDataExistMutableLiveData.postValue(it.data!!)
                    }
                    else -> {}
                }
                _uiState.postValue(UnloadingState)
            }
        }
    }
    fun searchProduct(filtersMap: HashMap<String, String>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            filterDataListOfGivenSheetUseCase.invoke(
                sheetName = Constants.SHEET_PRODUCTS,
                filters = filtersMap
            ).collect {
                _uiState.postValue(UnloadingState)

                when (it) {
                    is DataState.Success -> {
                        it.data?.let { response ->
                            showEmptyResponseMsg = response.isEmpty()
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

    fun showEmptyListMsg() = showEmptyResponseMsg


}