package com.app.igrow.data.local.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.igrow.utils.Constants


@Entity(tableName = Constants.SHEET_DEALERS)
data class DealersEntityName(
    @PrimaryKey
    var id: String = "",
    var dealer_name: String = "",
    var dealer_name_fr: String = "",
    var address: String = "",
    var address_fr: String = "",
    var city_town: String = "",
    var city_town_fr: String = "",
    var region: String = "",
    var region_fr: String = "",
    var telephone: String = "",
    var telephone_fr: String = "",
    var mobile: String = "",
    var mobile_fr: String = "",
    var distributors: String = "",
    var distributors_fr: String = "",
    var facebook:String = ""
)
