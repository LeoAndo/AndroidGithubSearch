package com.leoleo.androidgithubsearch.di

import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.GithubServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.http.*

import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideGithubService(): GithubService = GithubServiceImpl()
}