package com.app.igrow.utils

import android.app.Application
import com.app.igrow.R

class StringUtils(private val appContext: Application) {
    fun noNetworkErrorMessage() = appContext.getString(R.string.message_no_network_connected_str)
    fun somethingWentWrong() = appContext.getString(R.string.message_something_went_wrong_str)
    fun diagnosticDataSavedSuccessMsg() = appContext.getString(R.string.diagnostic_save_success)
    fun distributorDataSavedSuccessMsg() = appContext.getString(R.string.distributors_save_success)
    fun dealerDataSavedSuccessMsg() = appContext.getString(R.string.dealer_save_success)
    fun productsDataSavedSuccessMsg() = appContext.getString(R.string.products_save_success)
    fun noRecordFoundMsg() = appContext.getString(R.string.no_record_found)
    fun updateSuccesMsg() = appContext.getString(R.string.update_success)
    fun updateFailMsg() = appContext.getString(R.string.update_fail)
    fun deleteFailMsg() = appContext.getString(R.string.delete_fail)
    fun deleteSuccessMsg() = appContext.getString(R.string.delete_success)
}
