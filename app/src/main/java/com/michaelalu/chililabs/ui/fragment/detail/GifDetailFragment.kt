package com.michaelalu.chililabs.ui.fragment.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.michaelalu.chililabs.R
import com.michaelalu.chililabs.data.model.Data
import com.michaelalu.chililabs.data.model.GifData
import com.michaelalu.chililabs.databinding.FragmentGifDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GifDetailFragment : Fragment(R.layout.fragment_gif_detail) {

    private val viewModel: GifDetailViewModel by activityViewModels()
    private lateinit var binding: FragmentGifDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGifDetailBinding.bind(view)

        val gifId = arguments?.getString("id") ?: return

        viewModel.getDetailGif(gifId)

        observeGifDetail()
    }

    private fun observeGifDetail() {
        viewModel.gifDetailUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is GifDetailViewModel.UiState.Loading -> showLoading(true)
                is GifDetailViewModel.UiState.Success -> {
                    showLoading(false)
                    bindGifDetail(state.data.data)
                }
                is GifDetailViewModel.UiState.Error -> {
                    showLoading(false)
                    showError(state.message)
                }
                else -> Unit
            }
        }
    }

    private fun bindGifDetail(gif: GifData) {
        with(binding) {
            Glide.with(requireContext())
                .asGif()
                .load(gif.images?.original?.url)
                .placeholder(R.drawable.loader)
                .into(imgGifDetail)

            txtTitle.text = gif.title ?: "Untitled GIF"
            txtUsername.text = gif.username ?: "Unknown User"
            txtRating.text = "Rated: ${gif.rating?.uppercase() ?: "N/A"}"
            txtUploadDate.text = "Uploaded: ${gif.import_datetime ?: "N/A"}"

            btnShare.setOnClickListener {
                gif.images?.original?.url?.let { it1 -> shareGif(it1) }
            }
        }
    }

    private fun shareGif(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, "Check out this GIF: $url")
        }
        startActivity(Intent.createChooser(intent, "Share GIF via"))
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun showError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
