package com.app.igrow.data.local.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.igrow.utils.Constants


@Entity(tableName = Constants.SHEET_DISTRIBUTORS)
data class DistributorsEntityName(
    @PrimaryKey
    var id: String = "",
    var distributor_name: String = "",
    var distributor_name_fr: String = "",
    var address:String ="",
    var address_fr:String ="",
    var city_town:String="",
    var city_town_fr:String="",
    var region:String="",
    var region_fr:String="",
    var telephone: String = "",
    var telephone_2: String = "",
    var email: String = "",
    var email_fr: String = "",
    var website: String = "",
    var facebook: String = "",
)
