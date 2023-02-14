package com.leoleo.androidgithubsearch.data.api.response

@kotlinx.serialization.Serializable
internal data class GithubErrorResponse(val documentation_url: String, val message: String)