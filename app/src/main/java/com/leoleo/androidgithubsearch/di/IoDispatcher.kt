package com.leoleo.androidgithubsearch.di

import javax.inject.Qualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class DefaultDispatcher