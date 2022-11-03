package com.app.igrow.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.usecase.admin.diagnostic.FilterDataListOfGivenSheetUseCase
import com.app.igrow.data.usecase.user.general.GetColumnDataUsecase
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
    private val filterDataListOfGivenSheetUseCase: FilterDataListOfGivenSheetUseCase
) : ViewModel() {
    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getProductColumnDataMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var getProductColumnDataLiveData: MutableLiveData<ArrayList<String>?> =
        getProductColumnDataMutableLiveData

    private var filterResultMutableLiveData = MutableLiveData<ArrayList<HashMap<String, String>>>()
    var filtersLiveData: MutableLiveData<ArrayList<HashMap<String, String>>> =
        filterResultMutableLiveData

    fun getProductColumnData(columnName: String, sheetName: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getColumnDataUsecase.invoke(columnName = columnName, sheetName = sheetName).collect {
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