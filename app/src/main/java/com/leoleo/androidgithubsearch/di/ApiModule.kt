package com.leoleo.androidgithubsearch.di

import com.leoleo.androidgithubsearch.BuildConfig
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {
    private const val TIMEOUT_SEC: Long = 30

    @Singleton
    @Provides
    fun provideGithubService(
        okHttpClient: OkHttpClient,
        moshi: Moshi,
    ): GithubService {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.GITHUB_API_DOMAIN)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(debug: Boolean = BuildConfig.DEBUG): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        if (debug) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}