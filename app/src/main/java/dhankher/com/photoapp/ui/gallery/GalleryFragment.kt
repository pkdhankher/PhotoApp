package dhankher.com.photoapp.ui.gallery

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
import dhankher.com.photoapp.R
import dhankher.com.photoapp.data.FlickrPhotosData
import dhankher.com.photoapp.databinding.FragmentGalleryBinding
import dhankher.com.photoapp.onTextChanged

@AndroidEntryPoint
class GalleryFragment : Fragment(R.layout.fragment_gallery) {

    private val viewModel by viewModels<GalleryViewModel>()

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Photo App"

        _binding = FragmentGalleryBinding.bind(view)

        val adapter = GalleryAdapter()
        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.itemAnimator = null
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = FlickrPhotoLoadStateAdapter { adapter.retry() },
                footer = FlickrPhotoLoadStateAdapter { adapter.retry() }
            )
            buttonRetry.setOnClickListener { adapter.retry() }
            editSearch.onTextChanged {
                imageClose.isGone = it.isEmpty()
                if (it.isEmpty())
                    performSearch(FlickrPhotosData.DEFAULT_QUERY)
                else
                    performSearch(it)
            }
            imageClose.setOnClickListener {
                performSearch(FlickrPhotosData.DEFAULT_QUERY)
                editSearch.text.clear()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                textViewError.isVisible = loadState.source.refresh is LoadState.Error

                // empty view
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    recyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }

        performSearch(FlickrPhotosData.DEFAULT_QUERY)
    }

    private fun performSearch(query: String) {
        binding.recyclerView.scrollToPosition(0)
        viewModel.searchPhotos(query)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}