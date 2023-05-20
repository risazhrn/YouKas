package org.d3if3100.youkas.ui.catatan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.FragmentCatatanBinding

class CatatanFragment : Fragment() {

    private lateinit var binding: FragmentCatatanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.floatingAdd.setOnClickListener {
            findNavController().navigate(R.id.action_catatanFragment_to_addCatatanFragment)
        }
    }

}