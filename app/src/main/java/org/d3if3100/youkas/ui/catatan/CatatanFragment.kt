package org.d3if3100.youkas.ui.catatan

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.FragmentCatatanBinding
import org.d3if3100.youkas.db.RoomDB
import org.d3if3100.youkas.model.CatatanViewModel
import org.d3if3100.youkas.model.factory.CatatanViewModelFactory
import org.d3if3100.youkas.ui.adapter.CatatanAdapter
import java.text.NumberFormat
import java.util.*

class CatatanFragment : Fragment() {

    private var _binding: FragmentCatatanBinding? = null
    private var _catatanAdapter: CatatanAdapter? = null
    private val viewModel: CatatanViewModel by lazy {
        val db = RoomDB.getInstance(requireContext())
        val factory = CatatanViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[CatatanViewModel::class.java]
    }

    private val binding get() = _binding!!
    private val catatanAdapter get() = _catatanAdapter!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCatatanAdapter()
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
        var saldo = 0;
        viewModel.getTotalByJenis("Pemasukan").observe(viewLifecycleOwner) { it ->
            saldo += it.jumlah
            binding.tvJumlahMasuk.text = formatCurrency.format(it.jumlah)

            viewModel.getTotalByJenis("Pengeluaran").observe(viewLifecycleOwner) { it2 ->
                saldo -= it2.jumlah
                binding.tvJumKeluar.text = formatCurrency.format(it2.jumlah)
                binding.tvJumlahSaldo.text = formatCurrency.format(saldo)
            }
        }

        binding.floatingAdd.setOnClickListener {
            findNavController().navigate(R.id.action_catatanFragment_to_addCatatanFragment)
        }
    }

    private fun initCatatanAdapter() {
        _catatanAdapter = CatatanAdapter()
        with(binding.recyclerView) {
            adapter = catatanAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getAllCatatan.observe(viewLifecycleOwner) {
//            binding.tvJumlahMasuk.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            catatanAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _catatanAdapter = null
    }

}