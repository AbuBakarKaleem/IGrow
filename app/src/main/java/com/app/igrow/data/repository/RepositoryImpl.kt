package com.app.igrow.data.repository

import androidx.annotation.WorkerThread
import com.app.igrow.data.DataState
import com.app.igrow.data.remote.*
import com.app.igrow.data.model.CurrenciesDTO
import com.app.igrow.data.model.ExchangeRatesDTO
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */
class RepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils,
    private val apiService: ApiService,
) : Repository {

  /*  @WorkerThread
    override suspend fun getCurrencies(): Flow<DataState<CurrenciesDTO>> {
        return flow {
            apiService.getCurrencies().apply {
                this.onSuccessSuspend {
                    data?.let {
                        if(it.success) {
                            emit(DataState.success(it))
                        } else {
                            emit(DataState.error<CurrenciesDTO>(message = stringUtils.somethingWentWrong()))
                        }
                    }?:run{
                        emit(DataState.error<CurrenciesDTO>(message = stringUtils.somethingWentWrong()))
                    }
                }.onErrorSuspend {
                    emit(DataState.error<CurrenciesDTO>(message()))
                }.onExceptionSuspend {
                    if (this.exception is IOException) {
                        emit(DataState.error<CurrenciesDTO>(stringUtils.noNetworkErrorMessage()))
                    } else {
                        emit(DataState.error<CurrenciesDTO>(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }
    }

    override suspend fun getExchangeRates(): Flow<DataState<ExchangeRatesDTO>> {
        return flow {
            apiService.getExchangeRates().apply {
                this.onSuccessSuspend {
                    data?.let {
                        if (it.success) emit(DataState.success(it))
                        else emit(DataState.error<ExchangeRatesDTO>(stringUtils.somethingWentWrong()))
                    }
                }.onErrorSuspend {
                    emit(DataState.error<ExchangeRatesDTO>(message()))
                }.onExceptionSuspend {
                    if (this.exception is IOException) {
                        emit(DataState.error<ExchangeRatesDTO>(stringUtils.noNetworkErrorMessage()))
                    } else {
                        emit(DataState.error<ExchangeRatesDTO>(stringUtils.somethingWentWrong()))
                    }
                }
            }
        }
    }
*/
}
