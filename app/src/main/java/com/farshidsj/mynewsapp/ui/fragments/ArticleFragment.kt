package com.farshidsj.mynewsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.farshidsj.mynewsapp.databinding.FragmentArticleBinding
import com.farshidsj.mynewsapp.databinding.FragmentSearchNewsBinding
import com.farshidsj.mynewsapp.models.Article
import com.farshidsj.mynewsapp.ui.viewmodels.NewsViewModel
import com.farshidsj.mynewsapp.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private val viewModel: NewsViewModel by viewModels()
    private lateinit var args: Bundle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleBinding.inflate(layoutInflater)
        arguments?.let { args = it }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val article = args.getSerializable(Constants.ARTICLE) as Article
        setupWebView(article)
        setupFab(article)
    }

    private fun setupWebView(article: Article) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url!!)
        }
    }

    private fun setupFab(article: Article) {
        binding.fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(binding.root, "Article Saved Successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

}