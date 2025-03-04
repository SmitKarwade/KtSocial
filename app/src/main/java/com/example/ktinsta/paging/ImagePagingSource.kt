package com.example.ktinsta.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ktinsta.constants.Constants

import com.example.ktinsta.modal.Photo
import com.example.ktinsta.reqinterface.ImageService

class ImagePagingSource(
    private val apiService: ImageService
) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getImages(Constants.API_KEY, page = currentPage)
            val photos = response.photos ?: emptyList()

            LoadResult.Page(
                data = photos,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (photos.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}
