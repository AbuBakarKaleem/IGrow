package com.app.igrow.ui.products.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.usecase.user.general.GetDistributorByNameUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getDistributorByNameUsecase: GetDistributorByNameUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getDistributorByNameMutableLiveData = MutableLiveData<Distributors>()
    var getDistributorByNameDataLiveData: MutableLiveData<Distributors> =
        getDistributorByNameMutableLiveData

    fun getDistributorByName(name: String, columnName: String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getDistributorByNameUsecase.invoke(name,columnName).collect{
                _uiState.postValue(UnloadingState)
                when(it){
                    is DataState.Success ->{
                        it.data?.let { response ->
                            getDistributorByNameMutableLiveData.postValue(response)
                        }
                    }
                    is DataState.Error -> {
                        getDistributorByNameMutableLiveData.postValue(Distributors())
                    }
                }
            }
        }
    }
}