package com.app.igrow.ui.admin.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.usecase.admin.products.DeleteProductUsecase
import com.app.igrow.data.usecase.admin.products.GetProductUsecase
import com.app.igrow.data.usecase.admin.products.UpdateProductUsecase
import com.app.igrow.ui.admin.AdminUIStates
import com.app.igrow.ui.admin.LoadingState
import com.app.igrow.ui.admin.UnloadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val deleteProductUsecase: DeleteProductUsecase,
    private val updateProductUsecase: UpdateProductUsecase,
    private val getProductUsecase: GetProductUsecase
) : ViewModel() {

    private var _uiState = MutableLiveData<AdminUIStates>()
    var uiStateLiveData: LiveData<AdminUIStates> = _uiState

    private var getProductMutableLiveData = MutableLiveData<Any?>()
    var getProductLiveData: MutableLiveData<Any?> = getProductMutableLiveData

    private var updateProductMutableLiveData = MutableLiveData<String>()
    var updateProductLiveData: MutableLiveData<String> = updateProductMutableLiveData

    private var  deleteProductMutableLiveData = MutableLiveData<String>()
    var  deleteProductLiveData: MutableLiveData<String> =  deleteProductMutableLiveData

    fun getProduct(id:String) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            getProductUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        getProductMutableLiveData.postValue(it.data)
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        getProductMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
    fun deleteProduct(id: String){
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            deleteProductUsecase.invoke(id).collect {
                when (it) {
                    is DataState.Success -> {
                        deleteProductMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        deleteProductMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
    fun updateProduct(updatedProduct: HashMap<String,Products>) {
        _uiState.postValue(LoadingState)
        viewModelScope.launch {
            updateProductUsecase.invoke(updatedProduct).collect {
                when (it) {
                    is DataState.Success -> {
                        updateProductMutableLiveData.postValue(it.data.toString())
                        _uiState.postValue(UnloadingState)
                    }
                    is DataState.Error -> {
                        updateProductMutableLiveData.postValue(it.message)
                        _uiState.postValue(UnloadingState)
                    }
                }
            }
        }
    }
}