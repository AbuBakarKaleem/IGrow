package com.app.igrow.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.igrow.utils.Constants.TABLE_CURRENCY_RATES

@Entity(tableName = TABLE_CURRENCY_RATES)
data class CurrencyRatesEntity(
    @PrimaryKey
    var currencyName: String,
    var currencyExchangeValue: Double
)
