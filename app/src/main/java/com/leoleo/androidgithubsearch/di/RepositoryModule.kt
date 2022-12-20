package com.leoleo.androidgithubsearch.di

import com.leoleo.androidgithubsearch.data.repository.GithubRepositoryImpl
import com.leoleo.androidgithubsearch.domain.repository.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindGithubRepository(impl: GithubRepositoryImpl): GithubRepository
}