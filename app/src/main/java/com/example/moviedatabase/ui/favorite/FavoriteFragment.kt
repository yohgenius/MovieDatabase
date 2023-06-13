package com.example.moviedatabase.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedatabase.R
import com.example.moviedatabase.data.local.entity.DetailMovieEntity
import com.example.moviedatabase.databinding.FragmentFavoriteBinding
import com.example.moviedatabase.external.adapter.FavoriteMovieAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by viewModels()
    private val movieAdapter: FavoriteMovieAdapter = FavoriteMovieAdapter()
    private var pressedTime: Long = 0

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int =
            makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.bindingAdapterPosition
                val detailMovieEntitiy = movieAdapter.getSwipedData(swipedPosition)
                detailMovieEntitiy?.let { viewModel.setFavoriteMovie(it) }

                val snackbar =
                    Snackbar.make(view as View, R.string.message_undo, Snackbar.LENGTH_LONG)
                snackbar.setAction(R.string.message_ok) { _ ->
                    detailMovieEntitiy?.let { viewModel.setFavoriteMovie(it) }
                }
                snackbar.show()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showfavoriteMovie()
        itemTouchHelper.attachToRecyclerView(binding.rvMovies)
        backPress()
    }

    private fun showRvUser() {
        with(binding.rvMovies) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            adapter = movieAdapter
            movieAdapter.setOnItemClick(object : FavoriteMovieAdapter.OnItemClickCallback {
                override fun onItemClicked(data: DetailMovieEntity) {
                    val bundle = Bundle()
                    bundle.putString("movieId", data.id.toString())
                    findNavController().navigate(
                        R.id.action_favoriteFragment2_to_detailMovieFragment,
                        bundle
                    )
                }
            })
        }
    }

    private fun backPress() {
        //Back press Close App
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // handle back event
            if (pressedTime + 5000 > System.currentTimeMillis()) {
                activity?.finishAndRemoveTask()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.back_to_exit),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            pressedTime = System.currentTimeMillis()
        }
    }

    private fun showfavoriteMovie() {
        viewModel.getFavoriteMovie().observe(viewLifecycleOwner) { favoriteMovie ->
            if (favoriteMovie != null) {
                movieAdapter.submitList(null)
                movieAdapter.submitList(favoriteMovie)
                if (movieAdapter.itemCount < 1) {
                    binding.ivEmptyState.isVisible = true
                    binding.tvEmptyStateDesc.isVisible = true
                    binding.tvEmptyStateDesc.text = getString(R.string.no_favorite_movie)
                } else {
                    binding.ivEmptyState.isVisible = false
                    binding.tvEmptyStateDesc.isVisible = false

                }
                showRvUser()
            }
        }
    }


}