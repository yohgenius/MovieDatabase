package com.example.moviedatabase.ui.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ItemMovieListBinding
import com.example.moviedatabase.domain.model.ReviewModel
import com.example.moviedatabase.utils.GenreConverter

class ReviewAdapter : PagingDataAdapter<ReviewModel, ReviewAdapter.ViewHolder>(USER_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) {
            holder.bind(user)
        }
    }

    inner class ViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: ReviewModel
        ) {
            with(binding) {
                tvMovieTitle.text = data.author
                tvMovieGenre.text = data.content
            }
        }
    }

    companion object {
        private val USER_DIFF = object : DiffUtil.ItemCallback<ReviewModel>() {
            override fun areItemsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem.created_at == newItem.created_at
            }

            override fun areContentsTheSame(oldItem: ReviewModel, newItem: ReviewModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}