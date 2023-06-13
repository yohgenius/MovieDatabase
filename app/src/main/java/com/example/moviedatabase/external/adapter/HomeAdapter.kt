package com.example.moviedatabase.external.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.data.remote.response.GenreResult
import com.example.moviedatabase.databinding.ItemGenreListBinding
import kotlin.collections.ArrayList

class HomeAdapter(private val genres: ArrayList<GenreResult>) :
    RecyclerView.Adapter<HomeAdapter.DataViewHolder>() {


    class DataViewHolder(private val binding: ItemGenreListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: GenreResult) {
            var navController: NavController? = null
            binding.apply {
                tvMovieTitle.text = genre.name
                when ((position + 1) % 19) {
                    0 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color1
                        )
                    )
                    1 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color2
                        )
                    )
                    2 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color3
                        )
                    )
                    3 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color4
                        )
                    )
                    4 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color5
                        )
                    )
                    5 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color6
                        )
                    )
                    6 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color7
                        )
                    )
                    7 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color8
                        )
                    )
                    8 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color9
                        )
                    )
                    9 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color10
                        )
                    )
                    10 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color11
                        )
                    )
                    11 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color12
                        )
                    )
                    12 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color13
                        )
                    )
                    13 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color14
                        )
                    )
                    14 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color15
                        )
                    )
                    15 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color16
                        )
                    )
                    16 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color17
                        )
                    )
                    17 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color18
                        )
                    )
                    18 -> genreCard.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.color19
                        )
                    )
                }
                binding.genreCard.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("genreId", genre.id.toString())
                    navController = Navigation.findNavController(itemView)
                    navController!!.navigate(R.id.action_homeFragment2_to_discoverFragment, bundle)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {

        val binding =
            ItemGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = genres.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(genres[position])

    }

    fun addGenres(genres: List<GenreResult>) {
        this.genres.apply {
            addAll(genres)
        }

    }
}