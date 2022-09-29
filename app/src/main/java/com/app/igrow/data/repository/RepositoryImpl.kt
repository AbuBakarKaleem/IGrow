package com.app.igrow.data.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.remote.ApiService
import com.app.igrow.utils.Constants
import com.app.igrow.utils.Constants.DOCUMENT_ID
import com.app.igrow.utils.StringUtils
import com.app.igrow.utils.Utils
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.isActive
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */
class RepositoryImpl @Inject constructor(
    private val stringUtils: StringUtils
) : Repository {
    override suspend fun addDiagnosticData(diagnosticMap: HashMap<String, Diagnostic>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DIAGNOSTIC)
                    .document(DOCUMENT_ID).set(diagnosticMap)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.diagnosticDataSavedSuccessMsg())).isSuccess
                    }.addOnFailureListener { e ->
                        if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun addDistributorsData(distributorsMap: HashMap<String, Distributors>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DISTRIBUTORS)
                    .document(DOCUMENT_ID).set(distributorsMap)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.distributorDataSavedSuccessMsg())).isSuccess
                    }.addOnFailureListener { e ->
                        if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun addDealersData(dealers: HashMap<String, Dealers>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DEALERS)
                    .document(DOCUMENT_ID).set(dealers)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.dealerDataSavedSuccessMsg())).isSuccess
                    }.addOnFailureListener { e ->
                        if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun addProductsData(products: HashMap<String, Products>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_PRODUCTS)
                    .document(DOCUMENT_ID).set(products)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.productsDataSavedSuccessMsg())).isSuccess
                    }.addOnFailureListener { e ->
                        if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun getDiagnosticData(id: String): Flow<DataState<Any>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DIAGNOSTIC)
                    .document(DOCUMENT_ID).addSnapshotListener { snashot, error ->
                        if (error != null) {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                            return@addSnapshotListener
                        }
                        if (snashot!!.exists() && snashot.data!!.containsKey(id)) {
                            val map = snashot.data!![id] as HashMap<*, *>
                            val finalData = Utils.parseHashMapToObject(
                                map,
                                Diagnostic::class.java
                            ) as Diagnostic

                            if (isActive) trySend(DataState.success(finalData))
                        } else {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                        }

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<Any>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun updateDiagnostic(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DIAGNOSTIC)
                    .document(DOCUMENT_ID).update(diagnostic as Map<String, Any>)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.UpdateSuccesMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.UpdateFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }
}
