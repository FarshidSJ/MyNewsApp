package com.farshidsj.mynewsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farshidsj.mynewsapp.R
import com.farshidsj.mynewsapp.databinding.ItemArticlePreviewBinding
import com.farshidsj.mynewsapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemArticlePreviewBinding.bind(itemView)
    }

    private val differCallBack: DiffUtil.ItemCallback<Article> =
        object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_article_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = differ.currentList[position]
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

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}