package com.app.igrow.data.repository

import android.util.Log
import com.app.igrow.IGrowApp
import com.app.igrow.data.DataState
import com.app.igrow.data.local.repository.LocalRepository
import com.app.igrow.data.model.sheets.*
import com.app.igrow.data.remote.ApiService
import com.app.igrow.utils.*
import com.app.igrow.utils.Constants.SHEET_VIDEOS
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.WriteBatch
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

/**
 * This is an implementation of [Repository] to handle communication with [ApiService] server.
 */
class RepositoryImpl @Inject constructor(
    private val localRepository: LocalRepository,
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

                if (result.isSuccessful) {
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

    // General
    override suspend fun getColumnData(
        columnName: String,
        sheetName: String
    ): Flow<DataState<ArrayList<String>>> =
        callbackFlow {
            try {
                if (Utils.isInternetAvailable(IGrowApp.getInstance()).not()) {
                    val dataList =
                        getColumnDataFromLocal(sheetName = sheetName, columnName = columnName)
                    if (dataList.isEmpty().not()) {
                        if (isActive) trySend(DataState.success(dataList))
                    } else {
                        if (isActive) trySend(DataState.error<ArrayList<String>>(stringUtils.noRecordFoundMsg()))
                    }
                } else {
                    val databaseInstance = FirebaseFirestore.getInstance()
                    databaseInstance.collection(sheetName)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                if (isActive) trySend(DataState.error<ArrayList<String>>(stringUtils.noRecordFoundMsg())).isSuccess
                                Log.e("Firebase Error ", error.localizedMessage)
                                return@addSnapshotListener
                            }
                            if (snapshot != null && !snapshot.isEmpty) {
                                val dataList = ArrayList<String>()
                                snapshot.documents.forEach {
                                    var value: HashMap<String, String> = HashMap()
                                    value = if (it.data?.values?.asIterable()
                                            ?.elementAt(0) is HashMap<*, *>
                                    ) {
                                        it.data?.values?.first() as HashMap<String, String>
                                    } else {
                                        it.data?.values?.asIterable()
                                            ?.elementAt(1) as HashMap<String, String>
                                    }

                                    dataList.add(value[columnName].toString())
                                }
                                if (isActive) trySend(DataState.success(dataList.distinct() as ArrayList<String>))
                            } else {
                                if (isActive) trySend(DataState.error<ArrayList<String>>(stringUtils.noRecordFoundMsg()))
                            }
                        }
                }
            } catch (e: Exception) {
                if (isActive) trySend(DataState.error<ArrayList<String>>(e.message.toString()))
            }
            awaitClose()
        }

    override suspend fun searchByName(
        name: String,
        sheetName: String
    ): Flow<DataState<ArrayList<String>>> =
        callbackFlow {
            try {
                try {
                    val databaseInstance = FirebaseFirestore.getInstance()
                    databaseInstance.collection(sheetName)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                if (isActive) trySend(DataState.error<ArrayList<String>>(stringUtils.noRecordFoundMsg())).isSuccess
                                Log.e("Firebase Error ", error.localizedMessage)
                                return@addSnapshotListener
                            }
                            if (snapshot != null && snapshot.documents.isEmpty().not()) {
                                val dataList = ArrayList<String>()
                                snapshot.documents.forEach {
                                    dataList.add(it.get(name).toString())
                                    if (isActive) trySend(DataState.success(dataList))
                                }
                            } else {
                                if (isActive) trySend(DataState.error<ArrayList<String>>(stringUtils.noRecordFoundMsg()))
                            }
                        }
                } catch (e: Exception) {
                    if (isActive) trySend(DataState.error<ArrayList<String>>(e.message.toString()))
                }
            } catch (e: Exception) {
                if (isActive) trySend(DataState.error<ArrayList<String>>(e.message.toString()))
            }
            awaitClose()
        }

    override suspend fun getAllDataOfGivenSheet(sheetName: String): Flow<DataState<ArrayList<HashMap<String, String>>>> =
        callbackFlow {
            try {
                if (Utils.isInternetAvailable(IGrowApp.getInstance()).not()) {
                    val localDbData = getDataFromLocal(sheetName)
                    if (isActive) trySend(DataState.success(localDbData))
                } else {
                    val databaseInstance = FirebaseFirestore.getInstance()
                    databaseInstance.collection(sheetName)
                        .addSnapshotListener { snapshot, error ->
                            if (error != null) {
                                if (isActive) trySend(
                                    DataState.error<ArrayList<HashMap<String, String>>>(
                                        stringUtils.noRecordFoundMsg()
                                    )
                                ).isSuccess
                                Log.e("Firebase Error ", error.localizedMessage)
                                return@addSnapshotListener
                            }

                            if (snapshot != null && snapshot.documents.isEmpty().not()) {

                                val dataList = ArrayList<HashMap<String, String>>()

                                snapshot.documents.forEach { doc ->
                                    doc.data?.let { it ->
                                        var map: HashMap<String, String> = HashMap()
                                        map = if (it.values.asIterable()
                                                .elementAt(0) is HashMap<*, *>
                                        ) {
                                            it.values.first() as HashMap<String, String>
                                        } else {
                                            it.values.asIterable()
                                                .elementAt(1) as HashMap<String, String>
                                        }

                                        dataList.add(map)
                                    }
                                }

                                // inserting data into local DB for given sheetName
                                // getting data form local DB for given sheetName
                                CoroutineScope(Dispatchers.IO).launch {
                                    insertDataIntoDb(sheetName, dataList)
                                    val localDbData = getDataFromLocal(sheetName)
                                    if (isActive) trySend(DataState.success(localDbData))
                                }

                            } else {
                                if (isActive) trySend(
                                    DataState.error<ArrayList<HashMap<String, String>>>(
                                        stringUtils.noRecordFoundMsg()
                                    )
                                )
                            }
                        }
                }
            } catch (e: Exception) {
                if (isActive) trySend(DataState.error<ArrayList<HashMap<String, String>>>(e.message.toString()))
            }
            awaitClose()
        }

    private fun insertDataIntoDb(
        sheetName: String,
        dataList: ArrayList<HashMap<String, String>>,
    ) {
        CoroutineScope(Dispatchers.IO).launch {

            when (sheetName) {
                Constants.SHEET_DIAGNOSTIC -> {
                    val dbConvertedDBEntity = dataList.toDiagnosticEntityModel()
                    localRepository.getDiagnosticRepoImpl().insertDiagnostic(dbConvertedDBEntity)
                }
                Constants.SHEET_DEALERS -> {
                    val dbConvertedDBEntity = dataList.toDealerEntityModel()
                    localRepository.getDealersRepoImpl().insertDealers(dbConvertedDBEntity)
                }
                Constants.SHEET_DISTRIBUTORS -> {
                    val dbConvertedDBEntity = dataList.toDistributorEntityModel()
                    localRepository.getDistributorsImpl().insertDistributors(dbConvertedDBEntity)
                }
                Constants.SHEET_PRODUCTS -> {
                    val dbConvertedDBEntity = dataList.toProductsEntityModel()
                    localRepository.getProductsImpl().insertProducts(dbConvertedDBEntity)
                }
            }

        }
    }

    private suspend fun getDataFromLocal(
        sheetName: String
    ): ArrayList<HashMap<String, String>> {
        var result: ArrayList<HashMap<String, String>> = ArrayList()

        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            when (sheetName) {
                Constants.SHEET_DIAGNOSTIC -> {
                    result = localRepository.getDiagnosticRepoImpl().getAllDiagnostic()
                        .toDiagnosticUIModel()
                }
                Constants.SHEET_DEALERS -> {
                    result = localRepository.getDealersRepoImpl().getAllDealers()
                        .toDealerUIModel()
                }
                Constants.SHEET_DISTRIBUTORS -> {
                    result = localRepository.getDistributorsImpl().getAllDistributors()
                        .toDistributorUIModel()
                }
                Constants.SHEET_PRODUCTS -> {
                    result = localRepository.getProductsImpl().getAllProducts().toProductsUIModel()
                }
            }
        }
        return result
    }

    private suspend fun getColumnDataFromLocal(
        sheetName: String,
        columnName: String
    ): ArrayList<String> {
        var dataList = ArrayList<String>()
        withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
            when (sheetName) {
                Constants.SHEET_DIAGNOSTIC -> {
                    dataList =
                        localRepository.getDiagnosticRepoImpl()
                            .getDiagnosticColumnData(
                                sheetName = sheetName,
                                columnName = columnName
                            ) as ArrayList<String>
                }
                Constants.SHEET_DEALERS -> {
                    dataList =
                        localRepository.getDealersRepoImpl()
                            .getDealersColumnData(
                                sheetName = sheetName,
                                columnName = columnName
                            ) as ArrayList<String>
                }
                Constants.SHEET_DISTRIBUTORS -> {
                    dataList =
                        localRepository.getDistributorsImpl()
                            .getDistributorsColumnData(
                                sheetName = sheetName,
                                columnName = columnName
                            ) as ArrayList<String>
                }
                Constants.SHEET_PRODUCTS -> {
                    dataList =
                        localRepository.getProductsImpl()
                            .getProductsColumnData(
                                sheetName = sheetName,
                                columnName = columnName
                            ) as ArrayList<String>
                }
            }
        }

        return dataList
    }

    override suspend fun getLearningData(): Flow<DataState<ArrayList<Videos>>> = callbackFlow {
        val databaseInstance = FirebaseFirestore.getInstance()
        databaseInstance.collection(SHEET_VIDEOS).addSnapshotListener { snapshot, error ->
            if (error != null) {
                if (isActive) trySend(DataState.error<ArrayList<Videos>>(stringUtils.somethingWentWrong()))
                return@addSnapshotListener
            }
            if (snapshot != null && snapshot.isEmpty.not()) {
                val videosList = ArrayList<Videos>()
                for (doc in snapshot.documents) {
                    val videosObject = doc.toObject(Videos::class.java)
                    if (videosObject != null) {
                        videosList.add(videosObject)
                    }
                }
                if (isActive) trySend(DataState.success(videosList))
            } else {
                if (isActive) trySend(DataState.error<ArrayList<Videos>>(stringUtils.somethingWentWrong()))
            }
        }
        awaitClose()
    }

}


