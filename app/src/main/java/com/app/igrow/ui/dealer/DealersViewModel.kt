package com.app.igrow.ui.dealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.usecase.user.general.FilterDataListOfGivenSheetUseCase
import com.app.igrow.data.usecase.user.general.GetColumnDataUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import com.app.igrow.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealersViewModel@Inject constructor(
    private val getColumnDataUseCase: GetColumnDataUsecase,
    private val filterDataListOfGivenSheetUseCase: FilterDataListOfGivenSheetUseCase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDistributorColumnDataMutableLiveData = MutableLiveData<ArrayList<String>?>()
    var getDistributorColumnDataLiveData: MutableLiveData<ArrayList<String>?> =
        getDistributorColumnDataMutableLiveData

    private var filterResultMutableLiveData = MutableLiveData<ArrayList<HashMap<String, String>>>()
    var filtersLiveData: MutableLiveData<ArrayList<HashMap<String, String>>> =
        filterResultMutableLiveData

    private var showEmptyResponseMsg = false

    fun getDistributorColumnData(filtersMap: HashMap<String, String>,columnName: String, sheetName: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getColumnDataUseCase.invoke(filtersMap = filtersMap, columnName = columnName, sheetName = sheetName).collect {
                when (it) {
                    is DataState.Success -> {
                        getDistributorColumnDataMutableLiveData.postValue(it.data)
                    }
                    is DataState.Error -> {
                        getDistributorColumnDataMutableLiveData.postValue(null)
                    }

                }
                _uiState.postValue(UnloadingState)
            }
        }
    }

    fun searchDistributor(filtersMap: HashMap<String, String>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            filterDataListOfGivenSheetUseCase.invoke(
                sheetName = Constants.SHEET_DEALERS,
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

    fun showEmptyListMsg() =  showEmptyResponseMsg


}