package com.farshidsj.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.farshidsj.mynewsapp.R
import com.farshidsj.mynewsapp.adapters.NewsPagingAdapter
import com.farshidsj.mynewsapp.databinding.FragmentBreakingNewsBinding
import com.farshidsj.mynewsapp.ui.viewmodels.NewsViewModel
import com.farshidsj.mynewsapp.utils.Constants.Companion.ARTICLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class BreakingNewsFragment : Fragment() {

    private lateinit var binding: FragmentBreakingNewsBinding
    private lateinit var newsPagingAdapter: NewsPagingAdapter
    private val viewModel: NewsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupData()

    }

    private fun setupViews() {
        setupBreakingNews()
    }

    private fun setupData() {
        lifecycleScope.launchWhenCreated {
            viewModel.getBreakingNews().collectLatest {
                newsPagingAdapter.submitData(it)
            }
        }
    }

    private fun setupBreakingNews() {
        newsPagingAdapter = NewsPagingAdapter()
        binding.rvBreakingNews.apply {
            adapter = newsPagingAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }

        newsPagingAdapter.setOnItemClickListener { article ->
            val bundle = Bundle().apply {
                putSerializable(ARTICLE, article)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        newsPagingAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading
            ) {
                binding.paginationProgressBar.visibility = VISIBLE
            } else {
                binding.paginationProgressBar.visibility = GONE
            }
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                Toast.makeText(activity, it.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

}