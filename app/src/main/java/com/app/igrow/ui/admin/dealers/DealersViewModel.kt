package com.app.igrow.ui.admin.dealers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.usecase.admin.dealers.DeleteDealerUsecase
import com.app.igrow.data.usecase.admin.dealers.GetDealerUsecase
import com.app.igrow.data.usecase.admin.dealers.UpdateDealerUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealersViewModel @Inject constructor(
    private val getDealerUsecase: GetDealerUsecase,
    private val deleteDealerUsecase: DeleteDealerUsecase,
    private val updateDealerUsecase: UpdateDealerUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDealerMutableLiveData = MutableLiveData<Any?>()
    var getDealerLiveData: MutableLiveData<Any?> = getDealerMutableLiveData

    private var updateDealerMutableLiveData = MutableLiveData<String>()
    var updateDealerLiveData: MutableLiveData<String> = updateDealerMutableLiveData

    private var  deleteDealerMutableLiveData = MutableLiveData<String>()
    var  deleteDealerLiveData: MutableLiveData<String> =  deleteDealerMutableLiveData

    fun getDealer(id:String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getDealerUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        getDealerMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        getDealerMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
    fun deleteDealer(id: String){
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            deleteDealerUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        deleteDealerMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        deleteDealerMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
    fun updateDealer(updatedDealer: HashMap<String,Dealers>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            updateDealerUsecase.invoke(updatedDealer).collect {
                when (it) {
                    is DataState.Success -> {
                        updateDealerMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        updateDealerMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
}