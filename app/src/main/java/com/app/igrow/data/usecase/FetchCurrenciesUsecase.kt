package com.app.igrow.data.usecase

import com.app.igrow.data.DataState
import com.app.igrow.data.local.models.CurrencyNamesEntity
import com.app.igrow.data.local.repository.LocalRepository
import com.app.igrow.data.model.toDataBaseModel
import com.app.igrow.data.repository.Repository
import com.app.igrow.utils.StringUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCurrenciesUsecase @Inject constructor(
    private val repository: Repository,
    private val stringUtils: StringUtils,
    private val localRepo: LocalRepository
) {

   /* @ExperimentalCoroutinesApi
    suspend operator fun invoke(): Flow<DataState<List<CurrencyNamesEntity>>> {
        return flow {
            val responseFromLocalDatabase = localRepo.getAllCurrencyNames()
            if (responseFromLocalDatabase.isNullOrEmpty().not()) {
                emit(DataState.success(responseFromLocalDatabase))
            } else {
                val rates = repository.getCurrencies()
                rates.collect { response ->
                    when (response) {
                        is DataState.Success -> {
                            val currenciesList =
                                response.data?.toDataBaseModel() ?: emptyList<CurrencyNamesEntity>()
                            localRepo.insertCurrencyNames(currenciesList)
                            emit(DataState.success(currenciesList))
                        }
                        is DataState.Error -> {
                            emit(DataState.error<List<CurrencyNamesEntity>>(stringUtils.somethingWentWrong()))
                        }
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
    }
*/}
