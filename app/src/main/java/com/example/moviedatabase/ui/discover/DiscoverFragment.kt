package com.example.moviedatabase.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentDiscoverBinding
import com.example.moviedatabase.domain.model.MovieModel
import com.example.moviedatabase.external.adapter.DiscoverAdapter
import com.example.moviedatabase.external.adapter.UserLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DiscoverViewModel by viewModels()
    private val discoverAdapter: DiscoverAdapter = DiscoverAdapter()
    private var pressedTime: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscoverBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.movie.observe(viewLifecycleOwner) {
            discoverAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            binding.progressCircular.visibility = View.GONE
            showRvList()

        }
    }

    private fun showRvList() {
        with(binding.rvMovies) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = discoverAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter { discoverAdapter.retry() },
                footer = UserLoadStateAdapter { discoverAdapter.retry() }
            )

            binding.btnRetry.setOnClickListener {
                discoverAdapter.retry()
            }

            discoverAdapter.setOnItemCallback(object : DiscoverAdapter.OnItemClickCallback {
                override fun onItemClicked(data: MovieModel) {
                    val bundle = Bundle()
                    bundle.putString("movieId", data.id.toString())
                    findNavController().navigate(
                        R.id.action_discoverFragment_to_detailMovieFragment,
                        bundle
                    )
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}