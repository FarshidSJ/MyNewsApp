package com.farshidsj.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.farshidsj.mynewsapp.R
import com.farshidsj.mynewsapp.adapters.NewsPagingAdapter
import com.farshidsj.mynewsapp.databinding.FragmentSearchNewsBinding
import com.farshidsj.mynewsapp.ui.viewmodels.NewsViewModel
import com.farshidsj.mynewsapp.utils.Constants
import com.farshidsj.mynewsapp.utils.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchNewsFragment : Fragment() {

    private lateinit var binding: FragmentSearchNewsBinding
    private lateinit var newsPagingAdapter: NewsPagingAdapter
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()


    }

    private fun setupViews() {
        setupNews()
        setupSearchListener()

    }
    private fun setupSearchListener() {
        var job: Job? = null
        binding.etSearch.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.searchNews(editable.toString()).collectLatest {
                            newsPagingAdapter.submitData(it)
                        }
                    }
                }
            }
        }
    }

    private fun setupNews() {
        newsPagingAdapter = NewsPagingAdapter()
        binding.rvSearchNews.apply {
            adapter = newsPagingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        newsPagingAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable(Constants.ARTICLE, article)
            }
            findNavController().navigate(
                R.id.action_searchNewsFragment_to_articleFragment,
                bundle
            )
        }

        newsPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                binding.paginationProgressBar.visibility = View.VISIBLE
            } else {
                binding.paginationProgressBar.visibility = View.GONE
            }
        }
    }

}