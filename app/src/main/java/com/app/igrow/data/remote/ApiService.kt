package com.app.igrow.data.remote

import com.app.igrow.data.model.CurrenciesDTO
import com.app.igrow.data.model.ExchangeRatesDTO
import com.app.igrow.utils.Constants.CURRENCIES_END_POINT
import com.app.igrow.utils.Constants.DEFAULT_SOURCE_CURRENCY
import com.app.igrow.utils.Constants.LIVE_END_POINT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*@GET(LIVE_END_POINT)
    suspend fun getExchangeRates(
        @Query("source") source: String = DEFAULT_SOURCE_CURRENCY,
        @Query("access_key") apiKey: String = BuildConfig.API_KEY
    ): ApiResponse<ExchangeRatesDTO>

    @GET(CURRENCIES_END_POINT)
    suspend fun getCurrencies(
        @Query("access_key") apiKey: String = BuildConfig.API_KEY
    ): ApiResponse<CurrenciesDTO>*/

}
