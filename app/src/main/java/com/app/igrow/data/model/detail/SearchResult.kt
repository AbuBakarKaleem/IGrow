package com.app.igrow.data.model.detail

import android.os.Parcelable
import com.app.igrow.data.model.sheets.Diagnostic
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class SearchResult(
    var filterMap: @RawValue HashMap<String, String>,
    var searchResultList: @RawValue List<HashMap<String,String>>
) : Parcelable
