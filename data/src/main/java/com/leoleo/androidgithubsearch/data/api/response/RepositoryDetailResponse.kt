package com.leoleo.androidgithubsearch.data.api.response

import com.leoleo.androidgithubsearch.domain.model.RepositoryDetail

@kotlinx.serialization.Serializable
data class RepositoryDetailResponse(
    val forks_count: Int,
    val id: Int,
    val language: String?,
    val name: String,
    val open_issues_count: Int,
    val owner: Owner,
    val stargazers_count: Int,
    val watchers_count: Int,
) {

    @kotlinx.serialization.Serializable
    data class Owner(
        val avatar_url: String,
        val id: Int,
        val node_id: String,
    )
}

fun RepositoryDetailResponse.toModel(): com.leoleo.androidgithubsearch.domain.model.RepositoryDetail {
    return com.leoleo.androidgithubsearch.domain.model.RepositoryDetail(
        name = this.name,
        ownerAvatarUrl = this.owner.avatar_url,
        stargazersCount = this.stargazers_count.toString(),
        forksCount = this.forks_count.toString(),
        openIssuesCount = this.open_issues_count.toString(),
        watchersCount = this.watchers_count.toString(),
        language = this.language,
    )
}