package com.app.igrow.ui.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.usecase.admin.dealers.AddDealersUsecase
import com.app.igrow.data.usecase.admin.diagnostic.AddDiagnosticDataUsecase
import com.app.igrow.data.usecase.admin.distributors.AddDistributorsUsecase
import com.app.igrow.data.usecase.admin.products.AddProductsUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AdminActivityViewModel @Inject constructor(
    private val addDiagnosticUseCase: AddDiagnosticDataUsecase,
    private val addDistributorsUsecase: AddDistributorsUsecase,
    private val addDealersUsecase: AddDealersUsecase,
    private val addProductsUsecase: AddProductsUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var addDiagnosticMutableLiveData = MutableLiveData<String?>()
    var addDiagnosticLiveData: MutableLiveData<String?> = addDiagnosticMutableLiveData

    private var addDistributorsMutableLiveData = MutableLiveData<String?>()
    var addDistributorsLiveData: MutableLiveData<String?> = addDistributorsMutableLiveData

    private var addDealerMutableLiveData = MutableLiveData<String?>()
    var addDealerLiveData: MutableLiveData<String?> = addDealerMutableLiveData

    private var addProductsMutableLiveData = MutableLiveData<String?>()
    var addProductsLiveData: MutableLiveData<String?> = addProductsMutableLiveData

    fun addDiagnosticData(dataMap: HashMap<String,Diagnostic>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            addDiagnosticUseCase.invoke(dataMap).collect {
                when (it) {
                    is DataState.Success -> {
                        addDiagnosticMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        addDiagnosticMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    fun addDistributorsData(dataMap: HashMap<String,Distributors>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            addDistributorsUsecase.invoke(dataMap).collect {
                when (it) {
                    is DataState.Success -> {
                        addDistributorsLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        addDistributorsLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    fun addDealersData(dataMap: HashMap<String,Dealers>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            addDealersUsecase.invoke(dataMap).collect {
                when (it) {
                    is DataState.Success -> {
                        addDealerLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        addDealerLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    fun addProductsData(dataMap: HashMap<String,Products>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            addProductsUsecase.invoke(dataMap).collect {
                when (it) {
                    is DataState.Success -> {
                        addProductsLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        addProductsLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }

    //Helper Methods
    private fun diagnosticListToMap(diagnosticList: ArrayList<Diagnostic>): HashMap<String, Diagnostic> {
        val map = HashMap<String, Diagnostic>()
        try {
            diagnosticList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun distributorListToMap(dataList: ArrayList<Distributors>): HashMap<String, Distributors> {
        val map = HashMap<String, Distributors>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun dealersListToMap(dataList: ArrayList<Dealers>): HashMap<String, Dealers> {
        val map = HashMap<String, Dealers>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    private fun productsListToMap(dataList: ArrayList<Products>): HashMap<String, Products> {
        val map = HashMap<String, Products>()
        try {
            dataList.forEach {
                map[it.id] = it
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }


}