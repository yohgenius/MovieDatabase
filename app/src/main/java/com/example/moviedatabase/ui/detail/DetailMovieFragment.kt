package com.example.moviedatabase.ui.detail

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.moviedatabase.BuildConfig
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentDetailMovieBinding
import com.example.moviedatabase.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : Fragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailMovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = requireArguments().getString("movieId")
        if (id != null) {
            showDetailMovie(id)
            observeFavorite()
        }
        binding.icBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeFavorite() {
        viewModel.getDetail.observe(viewLifecycleOwner) { favorite ->
            if (favorite != null) {
                when (favorite.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE
                        if (favorite.data != null) {
                            val state = favorite.data.isFavorite
                            setFavoriteState(state)
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        favorite.message?.let {
                            Snackbar.make(
                                requireView(),
                                it, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.floatingActionButton.setImageResource(R.drawable.ic_favorite)
        } else binding.floatingActionButton.setImageResource(R.drawable.ic_favorite_off)

        binding.floatingActionButton.setOnClickListener {
            viewModel.setFavorite()
            if (state) {
                Snackbar.make(
                    requireView(),
                    getString(R.string.delete_user_favorite),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                Snackbar.make(
                    requireView(),
                    getString(R.string.add_user_favorite),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showDetailMovie(id: String) {
        viewModel.setDetailId(id)
        viewModel.getDetail.observe(viewLifecycleOwner) { detail ->
            if (detail != null) {
                when (detail.status) {
                    Status.SUCCESS -> {
                        if (detail.data != null) {
                            with(binding) {
                                progressBar.visibility = View.GONE
                                ivAvatar.clipToOutline = true
                                Glide.with(requireContext())
                                    .load(BuildConfig.IMG_URL + detail.data.backdropPath)
                                    .into(ivAvatar)
                                tvMovieTitle.text = detail.data.title
//                                val genreNames = mutableListOf<String>()
//                                tvMovieGenre.text = genreNames.joinToString(separator = ", ")
                                tvOverview.text = detail.data.overview
                                tvReview.setOnClickListener { }
                                tvTrailer.setOnClickListener { getTrailer(detail.data.id) }
                            }
                        }
                    }
                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE
                        detail.message?.let {
                            Snackbar.make(
                                requireView(),
                                it, Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    else -> {}
                }
            }
        }
    }

    private fun getTrailer(id: Int?) {
        viewModel.getTrailerLink(id.toString()).observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        for (i in 0..resource.data?.body()?.results?.size!!) {
                            if (resource.data.body()?.results!![i].type == "Trailer") {
                                openYoutubeLink(resource.data.body()?.results!![i].key)
                            }
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        println(it.message)
                    }
                    Status.LOADING -> {
                        println("Loading..")
                    }
                    Status.EMPTY -> TODO()
                }
            }
        }
    }

    fun openYoutubeLink(youtubeID: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeID))
        val intentBrowser =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + youtubeID))
        try {
            this.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            this.startActivity(intentBrowser)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}