package com.example.moviedatabase.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedatabase.data.remote.response.GenreResult
import com.example.moviedatabase.databinding.FragmentHomeBinding
import com.example.moviedatabase.external.adapter.HomeAdapter
import com.example.moviedatabase.external.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        binding.rvGenre.layoutManager = GridLayoutManager(context, 2)
        homeAdapter = HomeAdapter(arrayListOf())
        binding.rvGenre.adapter = homeAdapter

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getGenre().observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.rvGenre.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        resource.data?.let { movies ->
                            homeAdapter.addGenres(movies.body()!!.genres)
                        }
                    }
                    Status.ERROR -> {
                        binding.rvGenre.visibility = View.VISIBLE
                        binding.progressCircular.visibility = View.GONE
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        println(it.message)
                    }
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                        binding.rvGenre.visibility = View.GONE
                    }
                    Status.EMPTY -> {
                        binding.tvEmpty.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}