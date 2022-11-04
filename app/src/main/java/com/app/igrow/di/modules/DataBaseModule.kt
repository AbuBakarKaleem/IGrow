package com.app.igrow.di.modules

import android.content.Context
import com.app.igrow.data.local.AppDatabase
import com.app.igrow.data.local.abstraction.DealersRepo
import com.app.igrow.data.local.abstraction.DiagnosticRepo
import com.app.igrow.data.local.abstraction.DistributorsRepo
import com.app.igrow.data.local.abstraction.ProductsRepo
import com.app.igrow.data.local.dao.DealersDao
import com.app.igrow.data.local.dao.DiagnosticDao
import com.app.igrow.data.local.dao.DistributorsDao
import com.app.igrow.data.local.dao.ProductsDao
import com.app.igrow.data.local.repository.*
import com.app.igrow.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun providesLocalRepository(
        diagnosticRepo: DiagnosticRepo,
        productsRepo: ProductsRepo,
        dealersRepo: DealersRepo,
        distributorsRepo: DistributorsRepo
    ): LocalRepository {
        return LocalRepository(diagnosticRepo, productsRepo, dealersRepo, distributorsRepo)
    }

    @Singleton
    @Provides
    fun provideDiagnosticRepository(
        diagnosticDao: DiagnosticDao,
        stringUtils: StringUtils
    ): DiagnosticRepo {
        return DiagnosticRepoImpl(diagnosticDao, stringUtils)
    }

    @Singleton
    @Provides
    fun provideDealerRepository(dealersDao: DealersDao, stringUtils: StringUtils): DealersRepo {
        return DealersRepoImpl(dealersDao, stringUtils)
    }

    @Singleton
    @Provides
    fun provideProductsRepository(
        productsDao: ProductsDao,
        stringUtils: StringUtils
    ): ProductsRepo {
        return ProductsRepoImpl(productsDao, stringUtils)
    }

    @Singleton
    @Provides
    fun provideDistributorsRepository(
        distributorsDao: DistributorsDao,
        stringUtils: StringUtils
    ): DistributorsRepo {
        return DistributorsRepoImpl(distributorsDao, stringUtils)
    }

    @Singleton
    @Provides
    fun provideDiagnosticDao(appDatabase: AppDatabase): DiagnosticDao {
        return appDatabase.diagnosticDao()
    }

    @Singleton
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase): ProductsDao {
        return appDatabase.productsDao()
    }

    @Singleton
    @Provides
    fun provideDealerDao(appDatabase: AppDatabase): DealersDao {
        return appDatabase.dealersDao()
    }

    @Singleton
    @Provides
    fun provideDistributorsDao(appDatabase: AppDatabase): DistributorsDao {
        return appDatabase.distributorsDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

}