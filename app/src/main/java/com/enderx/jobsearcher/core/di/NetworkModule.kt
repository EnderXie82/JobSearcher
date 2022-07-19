package com.enderx.jobsearcher.core.di

import com.enderx.jobsearcher.BuildConfig
import com.enderx.jobsearcher.core.converter.MultipleConverter
import com.enderx.jobsearcher.core.network.NetworkConstant
import com.enderx.jobsearcher.core.network.NetworkConstant.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @Named(NetworkConstant.OKHTTP_REST)
    internal fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()

    @Singleton
    @Provides
    @Named(NetworkConstant.RETROFIT_REST)
    internal fun provideRetrofit(
        @Named(NetworkConstant.OKHTTP_REST) okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MultipleConverter())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
}