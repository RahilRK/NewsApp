package com.rahilkarim.newsapp.ui.fragments.SearchNews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahilkarim.newsapp.R
import com.rahilkarim.newsapp.models.Article
import kotlinx.android.synthetic.main.article_item.view.*

class SearchNewsAdapter: RecyclerView.Adapter<SearchNewsAdapter.ArticleViewHolder>() {

    private var onItemClickListener: ((Article) -> Unit)? = null

    private val diffCallback = object :DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.article_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val model = differ.currentList[position]
        holder.itemView.apply {

            Glide.with(this)
                .load(model.urlToImage)
                .into(ivArticleImage)

            tvSource.text = model.source.name
            tvTitle.text = model.title
            tvDescription.text = model.description
            tvPublishedAt.text = model.publishedAt

            setOnClickListener {
                onItemClickListener?.let {
                    it(model)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}