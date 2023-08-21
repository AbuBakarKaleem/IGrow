package com.app.igrow.data.model.sheets

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Distributors(
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
    var website: String = ""
): Parcelable
