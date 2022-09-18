package com.app.igrow.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.igrow.utils.Constants.TABLE_CURRENCY

@Entity(tableName = TABLE_CURRENCY)
data class CurrencyNamesEntity(
    @PrimaryKey
    val currencyName: String,
    var currencyCountryName: String
)
