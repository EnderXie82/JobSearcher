package com.enderx.jobsearcher.core.di

import android.content.Context
import com.enderx.jobsearcher.core.datastore.DataStoreRepository
import com.enderx.jobsearcher.core.datastore.DataStoreRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideDataStoreRepository(@ApplicationContext appContext: Context): DataStoreRepository =
        DataStoreRepositoryImpl(appContext)
}