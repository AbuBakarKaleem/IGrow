package com.app.igrow.ui.admin.distributors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.usecase.admin.distributors.DeleteDistributorUsecase
import com.app.igrow.data.usecase.admin.distributors.GetDistributorUsecase
import com.app.igrow.data.usecase.admin.distributors.UpdateDistributorUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DistributorViewModel @Inject constructor(
    private val getDistributorUsecase: GetDistributorUsecase,
    private val updateDistributorUsecase: UpdateDistributorUsecase,
    private val deleteDistributorUsecase: DeleteDistributorUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDistributorMutableLiveData = MutableLiveData<Any?>()
    var getDistributorLiveData: MutableLiveData<Any?> = getDistributorMutableLiveData

    private var updateDistributorMutableLiveData = MutableLiveData<String>()
    var updateDistributorLiveData: MutableLiveData<String> = updateDistributorMutableLiveData

    private var deleteDistributorMutableLiveData = MutableLiveData<String>()
    var deleteDistributorLiveData: MutableLiveData<String> = deleteDistributorMutableLiveData

    fun getDistributor(id: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getDistributorUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        getDistributorMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        getDistributorMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    fun deleteDistributor(id: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            deleteDistributorUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        deleteDistributorMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        deleteDistributorMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    fun updateDistributor(updatedDistributor: HashMap<String, Distributors>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            updateDistributorUsecase.invoke(updatedDistributor).collect {
                when (it) {
                    is DataState.Success -> {
                        updateDistributorMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        updateDistributorMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
}