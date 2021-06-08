package dhankher.com.photoapp.ui.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import dhankher.com.photoapp.data.FlickrPhoto
import dhankher.com.photoapp.databinding.ItemPhotoViewBinding


class GalleryAdapter() :
    PagingDataAdapter<FlickrPhoto, GalleryItemHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryItemHolder {
        val binding =
            ItemPhotoViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GalleryItemHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryItemHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto) =
                oldItem == newItem
        }
    }
}