package com.example.moviedatabase.external.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ItemMovieListBinding
import com.example.moviedatabase.databinding.ItemReviewListBinding
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.external.utils.GenreConverter
import java.text.SimpleDateFormat
import java.util.*

class ReviewAdapter : PagingDataAdapter<ReviewModel, ReviewAdapter.ViewHolder>(MOVIE_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class ViewHolder(private val binding: ItemReviewListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: ReviewModel
        ) {
            val parserFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val convertFormat =
                SimpleDateFormat("dd/MMM/yyyy, hh:mm a", Locale.getDefault())
            val date: Date? = parserFormat.parse(data.created_at)
            with(binding) {
                tvMovieTitle.text = data.author
                tvComment.text = data.content
                tvCreated.text = convertFormat.format(date)
            }
        }
    }

    companion object {
        private val MOVIE_DIFF = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem.created_at == newItem.created_at
            }

            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}