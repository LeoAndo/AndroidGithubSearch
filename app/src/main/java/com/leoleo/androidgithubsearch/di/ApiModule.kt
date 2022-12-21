package com.leoleo.androidgithubsearch.di

import com.leoleo.androidgithubsearch.BuildConfig
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ApiModule {
    private const val TIMEOUT_SEC: Long = 30

    @Singleton
    @Provides
    fun provideGithubService(
        okHttpClientBuilder: OkHttpClient.Builder,
        moshi: Moshi,
    ): GithubService {
        val okhttpClient = okHttpClientBuilder.apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()
                val originalUrl = request.url.toString()
                if (originalUrl.contains(BuildConfig.GITHUB_API_DOMAIN)) {
                    requestBuilder.addHeader("Accept", "application/vnd.github+json")
                    requestBuilder.addHeader(
                        "Authorization",
                        "Bearer ${BuildConfig.GITHUB_ACCESS_TOKEN}"
                    )
                    requestBuilder.addHeader("X-GitHub-Api-Version", "2022-11-28")
                }
                chain.proceed(requestBuilder.build())
            })
        }.build()
        return Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl(BuildConfig.GITHUB_API_DOMAIN)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GithubService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
}