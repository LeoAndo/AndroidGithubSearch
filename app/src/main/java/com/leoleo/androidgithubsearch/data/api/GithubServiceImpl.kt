package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.BuildConfig
import com.leoleo.androidgithubsearch.data.api.response.RepositoryDetailResponse
import com.leoleo.androidgithubsearch.data.api.response.SearchRepositoryResponse
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
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

class GithubServiceImpl : GithubService {
    private val format by lazy {
        Json { ignoreUnknownKeys = true }
    }
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

    override suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<RepositorySummary> {
        val response: HttpResponse = httpClient.get {
            url { path("search", "repositories") }
            parameter("q", query)
            parameter("page", page)
            parameter("per_page", perPage)
            parameter("sort", "stars")
        }
        val data =
            format.decodeFromString<SearchRepositoryResponse>(response.body())
        return data.toModel()
    }

    override suspend fun fetchRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail {
        val response: HttpResponse = httpClient.get {
            url {
                path("repos", ownerName, repositoryName)
            }
        }
        val data = format.decodeFromString<RepositoryDetailResponse>(response.body())
        return data.toModel()
    }

    companion object {
        private const val TIMEOUT_MILLIS: Long = 30 * 1000
    }
}