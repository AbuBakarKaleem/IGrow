package com.app.igrow.data.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import kotlinx.coroutines.flow.Flow

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize APIService.
 */
interface Repository {

    //Diagnostic
    suspend fun addDiagnosticData(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>>
    suspend fun getDiagnosticData(id:String): Flow<DataState<Any>>
    suspend fun getAllDiagnosticData(): Flow<DataState<ArrayList<HashMap<String,String>>>>
    suspend fun updateDiagnostic(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>>
    suspend fun deleteDiagnostic(id:String,diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>>

    //Distributors
    suspend fun addDistributorsData(distributors: HashMap<String, Distributors>): Flow<DataState<String>>
    suspend fun getDistributorsData(id:String): Flow<DataState<Any>>
    suspend fun updateDistributorsData(distributor: HashMap<String, Distributors>): Flow<DataState<String>>
    suspend fun deleteDistributorsData(id:String): Flow<DataState<String>>

    //Dealers
    suspend fun addDealersData(dealers: HashMap<String,Dealers>): Flow<DataState<String>>
    suspend fun getDealersData(id:String): Flow<DataState<Any>>
    suspend fun updateDealersData(dealers: HashMap<String, Dealers>): Flow<DataState<String>>
    suspend fun deleteDealersData(id:String): Flow<DataState<String>>

    //Products
    suspend fun addProductsData(products: HashMap<String, Products>): Flow<DataState<String>>
    suspend fun getProductsData(id:String): Flow<DataState<Any>>
    suspend fun updateProductsData(products: HashMap<String, Products>): Flow<DataState<String>>
    suspend fun deleteProductsData(id:String): Flow<DataState<String>>

    //Users Side repo's

    //Diagnostic
    suspend fun getColumnData(columnName:String,sheetName:String):Flow<DataState<ArrayList<String>>>
    suspend fun searchByName(name:String,sheetName:String):Flow<DataState<ArrayList<String>>>


}
