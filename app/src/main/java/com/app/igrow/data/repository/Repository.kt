package com.app.igrow.data.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.model.CurrenciesDTO
import com.app.igrow.data.model.ExchangeRatesDTO
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import kotlinx.coroutines.flow.Flow

/**
 * Repository is an interface data layer to handle communication with any data source such as Server or local database.
 * @see [RepositoryImpl] for implementation of this class to utilize APIService.
 */
interface Repository {

   /* suspend fun getCurrencies(): Flow<DataState<CurrenciesDTO>>
    suspend fun getExchangeRates(): Flow<DataState<ExchangeRatesDTO>>*/
    suspend fun addDiagnosticData(diagnostic: HashMap<String,Diagnostic>): Flow<DataState<String>>
    suspend fun addDistributorsData(distributors: ArrayList<Distributors>): Flow<DataState<String>>
    suspend fun addDealersData(dealers: ArrayList<Dealers>): Flow<DataState<String>>
}
