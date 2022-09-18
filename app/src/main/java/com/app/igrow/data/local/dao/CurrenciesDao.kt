package com.app.igrow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.igrow.data.local.models.CurrencyNamesEntity
import com.app.igrow.data.local.models.CurrencyRatesEntity
import com.app.igrow.utils.Constants.TABLE_CURRENCY
import com.app.igrow.utils.Constants.TABLE_CURRENCY_RATES

@Dao
abstract class CurrenciesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyNames(currencyNameEntities: List<CurrencyNamesEntity>)

    @Query("Select *FROM $TABLE_CURRENCY")
    abstract fun getAllCurrencyNames(): List<CurrencyNamesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCurrencyRates(currencyRateEntities: List<CurrencyRatesEntity>)

    @Query("Select *FROM $TABLE_CURRENCY_RATES")
    abstract fun getAllCurrencyRates(): List<CurrencyRatesEntity>
}