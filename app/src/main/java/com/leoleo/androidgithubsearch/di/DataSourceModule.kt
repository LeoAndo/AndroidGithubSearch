package com.leoleo.androidgithubsearch.di

import com.leoleo.androidgithubsearch.data.api.GithubApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

import javax.inject.Singleton

/**
 * ここに、Remote / Localのデータソースを定義する
 * ex: Ktor, Retrofit / Room, SharedPreferences, DataStore
 */
@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {
    @Singleton
    @Provides
    fun provideGithubService(format: Json): GithubApi = GithubApi(format)
}