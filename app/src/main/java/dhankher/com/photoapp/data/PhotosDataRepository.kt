package dhankher.com.photoapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import dhankher.com.photoapp.network.FlickrApi
import javax.inject.Inject

class PhotosDataRepository @Inject constructor(
    private val flickrApi: FlickrApi
) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FlickrPagingSource(flickrApi, query) }
        ).liveData
}