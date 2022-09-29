package com.app.igrow.utils

import android.app.Application
import com.app.igrow.R
import com.app.igrow.data.model.sheets.Diagnostic

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
    fun diagnosticDataSavedSuccessMsg() = appContext.getString(R.string.diagnostic_save_success)
    fun distributorDataSavedSuccessMsg() = appContext.getString(R.string.distributors_save_success)
    fun dealerDataSavedSuccessMsg() = appContext.getString(R.string.dealer_save_success)
    fun productsDataSavedSuccessMsg() = appContext.getString(R.string.products_save_success)
}
