package dhankher.com.photoapp.data


data class FlickrPhotosData(
    val photos: Photos,
    val stat: String
) {

    companion object{
        const val DEFAULT_QUERY = "Animal"
    }
    data class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val photo: List<FlickrPhoto>,
        val total: Int
    )
}