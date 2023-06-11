package org.d3if3100.youkas.ui.artikel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.FragmentNewsBinding
import org.d3if3100.youkas.model.NewsViewModel

class NewsFragment : Fragment() {

//    private lateinit var myAdapter: MyProductRecyclerViewAdapter
    private val viewModel: NewsViewModel by lazy {
        ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getData().observe(viewLifecycleOwner) {
//            myAdapter.updateData(it.articles)
//            Log.d("tes", it.toString())
        }

        viewModel.getStatus().observe(viewLifecycleOwner) {
//            updateProgress(it)
        }
    }
}