package com.leoleo.androidgithubsearch.data.di

import com.leoleo.androidgithubsearch.data.api.KtorHandler
import com.leoleo.androidgithubsearch.domain.di.IoDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object NetworkModule {
    @Singleton
    @Provides
    fun provideJson(): Json = Json { ignoreUnknownKeys = true }

    @Singleton
    @Provides
    fun provideKtorHandler(): KtorHandler = KtorHandler()
}