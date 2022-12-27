package com.leoleo.androidgithubsearch.data.api

import com.leoleo.androidgithubsearch.data.api.response.GithubSearchResponse
import com.leoleo.androidgithubsearch.data.api.response.RepositoryDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    companion object {
        const val SEARCH_PER_PAGE = 20
    }

    @GET("/search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int = SEARCH_PER_PAGE,
        @Query("sort") sort: String = "stars"
    ): GithubSearchResponse

    @GET("/repos/{owner_name}/{repository_name}")
    suspend fun fetchRepositoryDetail(
        @Path("owner_name") ownerName: String,
        @Path("repository_name") repositoryName: String,
    ): RepositoryDetailResponse
}