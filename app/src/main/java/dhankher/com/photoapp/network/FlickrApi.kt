package dhankher.com.photoapp.network

import dhankher.com.photoapp.data.FlickrPhotosData
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    companion object {
        const val BASE_URL = "https://api.flickr.com/services/rest/"
    }

    @GET("?method=flickr.photos.search&api_key=146543ae9f5436f5866a1b877a5e8def&format=json&nojsoncallback=1")
    suspend fun searchPhotos(
        @Query("tags") query: String,
        @Query("page") page: Int,
        @Query("perpage") perpage: Int
    ): FlickrPhotosData
}