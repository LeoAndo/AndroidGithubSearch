package com.leoleo.androidgithubsearch.di

import android.content.Context
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.StubGithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideGithubService(@ApplicationContext context: Context): GithubService =
        StubGithubService(context)
}