package com.leoleo.androidgithubsearch.domain.model

data class RepositoryDetail(
    val name: String,
    val ownerAvatarUrl: String,
    val stargazersCount: String,
    val forksCount: String,
    val openIssuesCount: String,
    val subscribersCount: String,
    val language: String?,
)
