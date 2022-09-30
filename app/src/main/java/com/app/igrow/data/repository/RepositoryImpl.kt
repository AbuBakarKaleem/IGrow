package com.app.igrow.data.repository

import com.app.igrow.data.DataState
import com.app.igrow.data.model.sheets.Dealers
import com.app.igrow.data.model.sheets.Diagnostic
import com.app.igrow.data.model.sheets.Distributors
import com.app.igrow.data.model.sheets.Products
import com.app.igrow.data.remote.ApiService
import com.app.igrow.utils.Constants
import com.app.igrow.utils.StringUtils
import com.app.igrow.utils.Utils
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
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

    // Diagnostic CRUD
    override suspend fun addDiagnosticData(diagnostic: HashMap<String, Diagnostic>): Flow<DataState<String>> =
        callbackFlow {
            try {
                val colRef: CollectionReference =
                    FirebaseFirestore.getInstance().collection(Constants.SHEET_DIAGNOSTIC)
                val batch: WriteBatch = FirebaseFirestore.getInstance().batch()
                for (data in diagnostic)
                    batch.set(colRef.document(data.key), data)
                val result = batch.commit()

                if (result.isSuccessful) {
                    if (isActive) trySend(DataState.success(stringUtils.diagnosticDataSavedSuccessMsg()))
                } else {
                   // if (isActive) trySend(DataState.error(stringUtils.somethingWentWrong()))
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
                    .document(id).addSnapshotListener { snashot, error ->
                        if (error != null) {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                            return@addSnapshotListener
                        }
                        if (snashot!!.exists() && snashot.data != null) {
                            val map = snashot.data!!.values.first() as HashMap<*, *>
                            val finalData = Utils.parseHashMapToObject(
                                map as HashMap<String, String>,
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
                    .document(diagnostic.keys.first()).update(diagnostic as Map<String, Any>)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.updateSuccesMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.updateFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    override suspend fun deleteDiagnostic(
        id: String,
        diagnosticMap: HashMap<String, Diagnostic>
    ): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DIAGNOSTIC)
                    .document(id)
                    .delete()
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.deleteSuccessMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.deleteFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    // Dealers CRUD
    override suspend fun addDealersData(dealers: HashMap<String, Dealers>): Flow<DataState<String>> =
        callbackFlow {
            try {
                val colRef: CollectionReference =
                    FirebaseFirestore.getInstance().collection(Constants.SHEET_DEALERS)
                val batch: WriteBatch = FirebaseFirestore.getInstance().batch()
                for (data in dealers)
                    batch.set(colRef.document(data.key), data)
                val result = batch.commit()

                if (result.isSuccessful) {
                    if (isActive) trySend(DataState.success(stringUtils.dealerDataSavedSuccessMsg()))
                } else {
                  //  if (isActive) trySend(DataState.error(stringUtils.somethingWentWrong()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun getDealersData(id: String): Flow<DataState<Any>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DEALERS)
                    .document(id).addSnapshotListener { snashot, error ->
                        if (error != null) {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                            return@addSnapshotListener
                        }
                        if (snashot!!.exists() && snashot.data != null) {
                            val map = snashot.data!!.values.first() as HashMap<*, *>
                            val finalData = Utils.parseHashMapToObject(
                                map as HashMap<String, String>,
                                Dealers::class.java
                            ) as Dealers
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

    override suspend fun updateDealersData(dealers: HashMap<String, Dealers>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DEALERS)
                    .document(dealers.keys.first()).update(dealers as Map<String, Any>)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.updateSuccesMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.updateFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    override suspend fun deleteDealersData(id: String): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DEALERS)
                    .document(id).delete()
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.deleteSuccessMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.deleteFailMsg())).isFailure
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    // Products Data
    override suspend fun addProductsData(products: HashMap<String, Products>): Flow<DataState<String>> =
        callbackFlow {
            try {
                val colRef: CollectionReference =
                    FirebaseFirestore.getInstance().collection(Constants.SHEET_PRODUCTS)
                val batch: WriteBatch = FirebaseFirestore.getInstance().batch()
                for (data in products)
                    batch.set(colRef.document(data.key), data)

                val result = batch.commit()

                if (result.isSuccessful) {
                    if (isActive) trySend(DataState.success(stringUtils.productsDataSavedSuccessMsg()))
                } else {
                   // if (isActive) trySend(DataState.error(stringUtils.somethingWentWrong()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun getProductsData(id: String): Flow<DataState<Any>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_PRODUCTS)
                    .document(id).addSnapshotListener { snashot, error ->
                        if (error != null) {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                            return@addSnapshotListener
                        }
                        if (snashot!!.exists() && snashot.data != null) {
                            val map = snashot.data!!.values.first() as HashMap<*, *>
                            val finalData = Utils.parseHashMapToObject(
                                map as HashMap<String, String>,
                                Products::class.java
                            ) as Products
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

    override suspend fun updateProductsData(products: HashMap<String, Products>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_PRODUCTS)
                    .document(products.keys.first()).update(products as Map<String, Any>)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.updateSuccesMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.updateFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    override suspend fun deleteProductsData(id: String): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_PRODUCTS)
                    .document(id).delete()
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.deleteSuccessMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.deleteFailMsg())).isFailure
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }


    // Distributors CRUD
    override suspend fun addDistributorsData(distributors: HashMap<String, Distributors>): Flow<DataState<String>> =
        callbackFlow {
            try {
                val colRef: CollectionReference =
                    FirebaseFirestore.getInstance().collection(Constants.SHEET_DISTRIBUTORS)
                val batch: WriteBatch = FirebaseFirestore.getInstance().batch()
                for (data in distributors)
                    batch.set(colRef.document(data.key), data)

                val result = batch.commit()

                if ( result.isSuccessful) {
                    if (isActive) trySend(DataState.success(stringUtils.distributorDataSavedSuccessMsg()))
                } else {
                  //  if (isActive) trySend(DataState.success(stringUtils.somethingWentWrong()))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isSuccess
            }
            awaitClose()
        }

    override suspend fun getDistributorsData(id: String): Flow<DataState<Any>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DISTRIBUTORS)
                    .document(id).addSnapshotListener { snashot, error ->
                        if (error != null) {
                            if (isActive) trySend(DataState.error<Any>(stringUtils.noRecordFoundMsg()))
                            return@addSnapshotListener
                        }
                        if (snashot!!.exists() && snashot.data != null) {
                            val map = snashot.data!!.values.first() as HashMap<*, *>
                            val finalData = Utils.parseHashMapToObject(
                                map as HashMap<String, String>,
                                Distributors::class.java
                            ) as Distributors
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

    override suspend fun updateDistributorsData(distributor: HashMap<String, Distributors>): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DISTRIBUTORS)
                    .document(distributor.keys.first()).update(distributor as Map<String, Any>)
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.updateSuccesMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.updateFailMsg())).isFailure

                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }

    override suspend fun deleteDistributorsData(id: String): Flow<DataState<String>> =
        callbackFlow {
            try {
                FirebaseFirestore.getInstance().collection(Constants.SHEET_DISTRIBUTORS)
                    .document(id).delete()
                    .addOnSuccessListener {
                        if (isActive) trySend(DataState.success(stringUtils.deleteSuccessMsg())).isSuccess
                    }.addOnFailureListener {
                        if (isActive) trySend(DataState.error(stringUtils.deleteFailMsg())).isFailure
                    }

            } catch (e: Exception) {
                e.printStackTrace()
                if (isActive) trySend(DataState.error<String>(e.message.toString())).isFailure
            }
            awaitClose()
        }
}
