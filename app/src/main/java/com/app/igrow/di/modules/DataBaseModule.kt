package com.app.igrow.di.modules

import android.content.Context
import com.app.igrow.data.local.AppDatabase
import com.app.igrow.data.local.dao.CurrenciesDao
import com.app.igrow.data.local.repository.LocalRepository
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
    fun providesLocalRepository(currenciesDao: CurrenciesDao): LocalRepository {
        return LocalRepository(currenciesDao)
    }

    @Singleton
    @Provides
    fun provideCurrenciesDao(appDatabase: AppDatabase): CurrenciesDao {
        return appDatabase.currenciesDao()
    }

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

}