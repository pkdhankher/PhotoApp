package dhankher.com.photoapp.ui.gallery

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dhankher.com.photoapp.R
import dhankher.com.photoapp.data.FlickrPhoto
import dhankher.com.photoapp.databinding.ItemPhotoViewBinding

class GalleryItemHolder(private val binding: ItemPhotoViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(photo: FlickrPhoto) {

        binding.apply {
            Glide.with(itemView)
                .load("https://farm${photo.farm}.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imagePhotoThumbnail)
        }
    }


}