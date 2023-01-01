package com.leoleo.androidgithubsearch.data.api.stub

import android.content.Context
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.response.RepositoryDetailResponse
import com.leoleo.androidgithubsearch.data.api.response.SearchRepositoryResponse
import com.leoleo.androidgithubsearch.data.api.response.toModel
import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary
import com.leoleo.androidgithubsearch.data.api.stub.extentions.decodeFromStubData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

/**
 * サーバーサイドのAPI開発が完了するまでstub環境で開発を進めたい場合は、
 * こちらのクラスにDataSourceModuleを書き換えてください。
 */
class StubGithubService @Inject constructor(
    @ApplicationContext private val context: Context,
    private val format: Json
) : GithubService {
    override suspend fun searchRepositories(
        query: String,
        page: Int,
        perPage: Int,
        sort: String
    ): List<RepositorySummary> {
        return format.decodeFromStubData<SearchRepositoryResponse>(
            context,
            format,
            "search_repositories_success.json"
        ).toModel()
    }

    override suspend fun fetchRepositoryDetail(
        ownerName: String,
        repositoryName: String
    ): RepositoryDetail {
        return format.decodeFromStubData<RepositoryDetailResponse>(
            context,
            format,
            "fetch_repository_detail_success.json"
        ).toModel()
    }
}