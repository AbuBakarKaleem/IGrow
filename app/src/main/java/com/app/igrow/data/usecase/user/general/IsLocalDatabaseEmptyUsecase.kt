package com.app.igrow.data.usecase.user.general

import com.app.igrow.data.local.repository.LocalRepository
import javax.inject.Inject

class IsLocalDatabaseEmptyUsecase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(): Boolean {

        val diagnoseCount = localRepository.getDiagnosticRepoImpl().getDiagnosticCount()
        val productsCount = localRepository.getProductsImpl().getProductsCount()
        val distributorsCount = localRepository.getDistributorsImpl().getDistributorsCount()
        val dealersCount = localRepository.getDealersRepoImpl().getDealersCount()

        if (diagnoseCount > 0 && productsCount > 0 && distributorsCount > 0 && dealersCount > 0) {
            return false
        }

        return true

    }
}
