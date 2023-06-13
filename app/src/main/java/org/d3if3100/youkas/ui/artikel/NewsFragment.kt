package org.d3if3100.youkas.ui.artikel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3100.youkas.databinding.FragmentNewsBinding
import org.d3if3100.youkas.model.NewsViewModel
import org.d3if3100.youkas.network.NewsApi
import org.d3if3100.youkas.ui.adapter.NewsAdapter

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private var _newsAdapter: NewsAdapter? = null

    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private val binding get() = _binding!!
    private val newsAdapter get() = _newsAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        _newsAdapter = NewsAdapter()
        with(binding.recyclerView) {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) {
            it.articles?.let { it1 -> newsAdapter.updateData(it1) }
        }

        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateProgress(it)
        }
    }

    private fun updateProgress(status: NewsApi.ApiStatus?) {
        with(binding) {
            when (status) {
                NewsApi.ApiStatus.LOADING -> progressBar.visibility = View.VISIBLE
                NewsApi.ApiStatus.SUCCESS -> progressBar.visibility = View.GONE
                NewsApi.ApiStatus.FAILED -> {
                    progressBar.visibility = View.GONE
                    networkError.visibility = View.VISIBLE
                }
                else -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _newsAdapter = null
    }
}