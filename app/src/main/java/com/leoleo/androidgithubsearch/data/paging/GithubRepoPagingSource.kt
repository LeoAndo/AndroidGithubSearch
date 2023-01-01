package com.leoleo.androidgithubsearch.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.leoleo.androidgithubsearch.data.api.GithubService
import com.leoleo.androidgithubsearch.data.api.KtorHandler
import com.leoleo.androidgithubsearch.domain.model.RepositorySummary

class GithubRepoPagingSource(
    private val query: String,
    private val api: GithubService,
    private val ktorHandler: KtorHandler,
) : PagingSource<Int, RepositorySummary>() {

    override fun getRefreshKey(state: PagingState<Int, RepositorySummary>): Int? {
        val key = state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
        Log.d(TAG, "getRefreshKey: $key")
        return key
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepositorySummary> {
        return try {
            val pageNumber = params.key ?: INIT_PAGE_NO
            val size = params.loadSize
            val from = pageNumber * size
            val placeholdersEnabled = params.placeholdersEnabled
            val data =
                ktorHandler.dataOrThrow {
                    api.searchRepositories(query = query, page = pageNumber)
                }
            // Since {INIT_PAGE_NO} is the lowest page number, return null to signify no more pages should
            // be loaded before it.
            val prevKey = if (pageNumber > INIT_PAGE_NO) pageNumber - 1 else null
            // This Github API defines that it's out of data when a page returns empty. When out of
            // data, we return `null` to signify no more pages should be loaded
            val nextKey = if (data.isNotEmpty()) pageNumber + 1 else null
            Log.d(
                TAG,
                "pageNumber: $pageNumber size: $size from: $from placeholdersEnabled: $placeholdersEnabled prevKey: $prevKey nextKey: $nextKey"
            )
            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == INIT_PAGE_NO) null else pageNumber - 1,
                nextKey = if (data.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val INIT_PAGE_NO = 1
        private const val TAG = "GithubRepoPagingSource"
    }
}