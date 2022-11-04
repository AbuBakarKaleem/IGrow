package com.app.igrow.data.local.repository

import com.app.igrow.data.local.abstraction.DealersRepo
import com.app.igrow.data.local.abstraction.DiagnosticRepo
import com.app.igrow.data.local.abstraction.DistributorsRepo
import com.app.igrow.data.local.abstraction.ProductsRepo
import javax.inject.Inject

class LocalRepository @Inject constructor(
    private var diagnosticRepo: DiagnosticRepo,
    private var productsRepo: ProductsRepo,
    private var dealersRepo: DealersRepo,
    private var distributorsRepo: DistributorsRepo
) {

    fun getDiagnosticRepoImpl() = diagnosticRepo
    fun getDealersRepoImpl() = dealersRepo
    fun getDistributorsImpl() = distributorsRepo
    fun getProductsImpl() = productsRepo

}