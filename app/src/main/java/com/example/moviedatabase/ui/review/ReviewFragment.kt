package com.example.moviedatabase.ui.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.databinding.FragmentDiscoverBinding
import com.example.moviedatabase.external.adapter.ReviewAdapter
import com.example.moviedatabase.external.adapter.UserLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReviewFragment : Fragment() {

    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ReviewViewModel by viewModels()
    private val reviewAdapter: ReviewAdapter = ReviewAdapter()
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

        viewModel.review.observe(viewLifecycleOwner) {
            reviewAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            binding.progressCircular.visibility = View.GONE
            showRvList()
        }
    }

    private fun showRvList() {
        with(binding.rvMovies) {
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
            this.adapter = reviewAdapter.withLoadStateHeaderAndFooter(
                header = UserLoadStateAdapter { reviewAdapter.retry() },
                footer = UserLoadStateAdapter { reviewAdapter.retry() }
            )

            binding.btnRetry.setOnClickListener {
                reviewAdapter.retry()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}