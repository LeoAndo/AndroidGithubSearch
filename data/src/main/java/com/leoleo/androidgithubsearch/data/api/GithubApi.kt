package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.data.BuildConfig
import com.leoleo.androidgithubsearch.data.api.response.RepositoryDetailResponse
import com.leoleo.androidgithubsearch.data.api.response.SearchRepositoryResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

internal class GithubApi(private val format: Json, private val ktorHandler: KtorHandler) {
    private val httpClient: HttpClient by lazy {
        HttpClient(Android) {
            defaultRequest {
                url.takeFrom(URLBuilder().takeFrom(BuildConfig.GITHUB_API_DOMAIN).apply {
                    encodedPath += url.encodedPath
                })
                header("Accept", "application/vnd.github.v3+json")
                header("Authorization", "Bearer ${BuildConfig.GITHUB_ACCESS_TOKEN}")
                header("X-GitHub-Api-Version", "2022-11-28")
            }
            install(HttpTimeout) {
                requestTimeoutMillis = TIMEOUT_MILLIS
                connectTimeoutMillis = TIMEOUT_MILLIS
                socketTimeoutMillis = TIMEOUT_MILLIS
            }
            install(Logging) {
                logger = AppHttpLogger()
                level = LogLevel.BODY
            }
            expectSuccess = true
        }
    }

    suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int = SEARCH_PER_PAGE,
        sort: String = "stars"
    ): SearchRepositoryResponse {
        return ktorHandler.dataOrThrow {
            /*
                // サーバーサイドのAPI開発が完了するまではFlavorをstubにし、開発を進める.
                format.decodeFromStubData<SearchRepositoryResponse>(
                    context,
                    format,
                    "search_repositories_success.json"
                )
             */
            val response: HttpResponse = httpClient.get {
                url { path("search", "repositories") }
                parameter("q", query)
                parameter("page", page)
                parameter("per_page", perPage)
                parameter("sort", sort)
            }
            format.decodeFromString(response.body())
        }
    }

    suspend fun fetchRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetailResponse {
        return ktorHandler.dataOrThrow {
            val response: HttpResponse = httpClient.get {
                url {
                    path("repos", ownerName, repositoryName)
                }
            }
            format.decodeFromString(response.body())
        }
    }

    companion object {
        const val SEARCH_PER_PAGE = 20
        private const val TIMEOUT_MILLIS: Long = 30 * 1000
    }
}