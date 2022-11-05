package com.app.igrow.di.modules

import android.app.Application
import com.app.igrow.data.local.repository.LocalRepository
import com.app.igrow.data.repository.Repository
import com.app.igrow.data.repository.RepositoryImpl
import com.app.igrow.utils.StringUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * The Dagger Module for providing repository instances.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideStringUtils(app: Application): StringUtils {
        return StringUtils(app)
    }

    @Singleton
    @Provides
    fun provideRepository(stringUtils: StringUtils, localRepository: LocalRepository): Repository {
        return RepositoryImpl(localRepository,stringUtils)
    }
}
