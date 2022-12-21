package com.leoleo.androidgithubsearch.domain.model

data class RepositoryDetail(
    val name: String,
    val ownerAvatarUrl: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val openIssuesCount: Int,
    val subscribersCount: Int,
    val language: String?,
)
