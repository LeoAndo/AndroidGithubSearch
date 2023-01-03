package com.leoleo.androidgithubsearch.data.di

import com.leoleo.androidgithubsearch.data.repository.GithubRepoRepositoryImpl
import com.leoleo.androidgithubsearch.domain.repository.GithubRepoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindGithubRepoRepository(impl: GithubRepoRepositoryImpl): GithubRepoRepository
}