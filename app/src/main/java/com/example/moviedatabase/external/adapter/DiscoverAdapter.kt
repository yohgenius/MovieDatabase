package com.example.moviedatabase.external.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.ItemMovieListBinding
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.external.utils.GenreConverter

class DiscoverAdapter : PagingDataAdapter<MovieModel, DiscoverAdapter.ViewHolder>(MOVIE_DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class ViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: MovieModel
        ) {
            with(binding) {
                Glide.with(itemView.context).load(BuildConfig.IMG_URL+data.posterPath).placeholder(R.drawable.ic_failed)
                    .error(R.drawable.ic_failed).into(ivAvatar)
                tvMovieTitle.text = data.title
                tvMovieGenre.text = GenreConverter.getGenre(data.genre_ids)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }

    }

    lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemCallback(callback: OnItemClickCallback) {
        this.onItemClickCallback = callback

    }

    interface OnItemClickCallback {
        fun onItemClicked(data: MovieModel)
    }


    companion object {
        private val MOVIE_DIFF = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}