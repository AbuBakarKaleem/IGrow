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

    suspend fun addDiagnosticData(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>>
    suspend fun addDistributorsData(distributors: HashMap<String, Distributors>): Flow<DataState<String>>
    suspend fun addDealersData(dealers: HashMap<String,Dealers>): Flow<DataState<String>>
    suspend fun addProductsData(dealers: HashMap<String, Products>): Flow<DataState<String>>
    suspend fun getDiagnosticData(id:String): Flow<DataState<Any>>
    suspend fun updateDiagnostic(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>>

}
