package dhankher.com.photoapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dhankher.com.photoapp.network.FlickrApi
import retrofit2.HttpException
import java.io.IOException

private const val FLICKR_STARTING_PAGE_INDEX = 1

class FlickrPagingSource(
    private val flickrApi: FlickrApi,
    private val query: String
) : PagingSource<Int, FlickrPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, FlickrPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickrPhoto> {
        val position = params.key ?: FLICKR_STARTING_PAGE_INDEX

        return try {
            val response = flickrApi.searchPhotos(query, position, params.loadSize)
            val photos = response.photos.photo
            LoadResult.Page(
                data = photos,
                prevKey = if (position == FLICKR_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (photos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}