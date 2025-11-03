package com.michaelalu.chililabs.ui.fragment.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.michaelalu.chililabs.R
import com.michaelalu.chililabs.databinding.FragmentSearchBinding
import com.michaelalu.chililabs.ui.adapter.GifListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private val mAdapter: GifListAdapter by lazy {
        GifListAdapter { gif ->
            findNavController().navigate(
                R.id.action_searchFragment_to_detailsFragment,
                bundleOf("id" to gif.id)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        setupSearchBar()
        observeUiState()
    }

    private fun initRecycler() = with(binding.rvGifList) {
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = mAdapter
    }

    private fun setupSearchBar() {
        with(binding) {
            btnClear.setOnClickListener {
                edtAddress.setText("")
            }

            edtAddress.addTextChangedListener { editable ->
                val query = editable?.toString()?.trim().orEmpty()
                viewModel.searchGif(query)
                btnClear.isVisible = query.isNotEmpty()
            }

            btnRetry.setOnClickListener {
                val query = edtAddress.text.toString().trim()
                if (query.isNotEmpty()) viewModel.searchGif(query)
            }
        }
    }

    private fun observeUiState() {
        viewModel.searchUiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchViewModel.UiState.Loading -> showLoading()
                is SearchViewModel.UiState.Success -> showResults(state)
                is SearchViewModel.UiState.Empty -> showEmpty()
                is SearchViewModel.UiState.Error -> showError(state.message)
                else -> Unit
            }
        }
    }

    private fun showLoading() = with(binding) {
        progressBar.isVisible = true
        rvGifList.isVisible = false
        txtEmpty.isVisible = false
        layoutError.isVisible = false
    }

    private fun showResults(state: SearchViewModel.UiState.Success<*>) = with(binding) {
        progressBar.isVisible = false
        layoutError.isVisible = false
        txtEmpty.isVisible = false
        rvGifList.isVisible = true
        @Suppress("UNCHECKED_CAST")
        val pagingData = state.data as? androidx.paging.PagingData<com.michaelalu.chililabs.data.model.Data>
        pagingData?.let { mAdapter.submitData(lifecycle, it) }
    }

    private fun showEmpty() = with(binding) {
        progressBar.isVisible = false
        layoutError.isVisible = false
        txtEmpty.isVisible = true
        rvGifList.isVisible = false
    }

    private fun showError(message: String) = with(binding) {
        progressBar.isVisible = false
        rvGifList.isVisible = false
        txtEmpty.isVisible = false
        layoutError.isVisible = true
        txtError.text = message
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
