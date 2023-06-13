package com.example.moviedatabase.external.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.databinding.ItemMovieListBinding
import com.example.moviedatabase.external.utils.GenreConverter

class FavoriteMovieAdapter : ListAdapter<DetailMovieEntity, FavoriteMovieAdapter.ViewHolder>(
    MOVIE_DIFF
) {
    lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClick(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    companion object {
        private val MOVIE_DIFF = object : DiffUtil.ItemCallback<DetailMovieEntity>() {
            override fun areItemsTheSame(
                oldItem: DetailMovieEntity,
                newItem: DetailMovieEntity
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DetailMovieEntity,
                newItem: DetailMovieEntity
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class ViewHolder(private val binding: ItemMovieListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DetailMovieEntity) {
            with(binding) {
                Glide.with(itemView)
                    .load(BuildConfig.IMG_URL+data.poster_path)
                    .into(ivAvatar)
                tvMovieTitle.text = data.title
//                tvMovieGenre.text = GenreConverter.getGenre(data.)
                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMovieListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    fun getSwipedData(swipedPosition: Int): DetailMovieEntity? = getItem(swipedPosition)

    interface OnItemClickCallback {
        fun onItemClicked(data: DetailMovieEntity)
    }
}