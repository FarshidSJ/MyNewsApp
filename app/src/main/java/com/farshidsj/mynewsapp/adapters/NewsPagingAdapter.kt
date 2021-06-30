package com.farshidsj.mynewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farshidsj.mynewsapp.R
import com.farshidsj.mynewsapp.databinding.ItemArticlePreviewBinding
import com.farshidsj.mynewsapp.models.Article

class NewsPagingAdapter : PagingDataAdapter<Article, NewsPagingAdapter.ArticleViewHolder>(DiffUtilCallBack()) {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemArticlePreviewBinding.bind(itemView)
    }

    /*private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }*/


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        with(holder) {
            binding.root.apply {
                Glide.with(this).load(article?.urlToImage).into(binding.ivArticleImage)
                binding.tvSource.text = article?.source?.name
                binding.tvTitle.text = article?.title
                binding.tvDescription.text = article?.description
                binding.tvPublishedAt.text = article?.publishedAt
                setOnClickListener {
                    onItemClickListener?.let { it(article!!) }
                }
            }
        }

    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }

    class DiffUtilCallBack: DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }
}