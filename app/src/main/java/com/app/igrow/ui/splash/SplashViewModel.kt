package com.app.igrow.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.usecase.user.general.FilterDataListOfGivenSheetUseCase
import com.app.igrow.data.usecase.user.general.IsLocalDatabaseEmptyUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLocalDatabaseEmptyUsecase: IsLocalDatabaseEmptyUsecase,
    private val filterDataListOfGivenSheetUseCase: FilterDataListOfGivenSheetUseCase
) : ViewModel() {

    init {
        isDataBaseEmpty()
    }

    private val _uiState = MutableLiveData<AdminUIStates>()
    val uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private val isLocalDatBaseEmpty = MutableLiveData<Boolean>()
    val isLocalDatBaseEmptyLiveData: LiveData<Boolean> = isLocalDatBaseEmpty

    private fun isDataBaseEmpty() {
        viewModelScope.launch(Dispatchers.IO) {
            val isDatebaseEmpty =
                isLocalDatabaseEmptyUsecase.invoke()
            isLocalDatBaseEmpty?.let {
                it.postValue(isDatebaseEmpty)
            }
        }
    }

    fun insertDataForGivenTable(sheetName: String) {
        val filtersMap = HashMap<String, String>()
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            filterDataListOfGivenSheetUseCase.invoke(
                sheetName = sheetName,
                filters = filtersMap
            ).collect {
                _uiState.postValue(UnloadingState)

                when (it) {
                    is DataState.Success -> {
                        it.data?.let { response ->
                            //filterResultMutableLiveData.postValue(response)
                            // do nothing
                        }
                    }
                    is DataState.Error -> {
                        //filterResultMutableLiveData.postValue(arrayListOf<HashMap<String, String>>())
                        // do nothing
                    }
                }
            }
        }
    }
}