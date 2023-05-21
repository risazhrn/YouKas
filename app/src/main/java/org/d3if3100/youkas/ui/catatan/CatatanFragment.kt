package org.d3if3100.youkas.ui.catatan

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import org.d3if3100.youkas.R
import org.d3if3100.youkas.data.SettingsDataStore
import org.d3if3100.youkas.data.dataStore
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
    private var saldo: Int = 0;
    private val viewModel: CatatanViewModel by lazy {
        val db = RoomDB.getInstance(requireContext())
        val factory = CatatanViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[CatatanViewModel::class.java]
    }

    private lateinit var myAdapter: CatatanAdapter

    private val binding get() = _binding!!
    private val catatanAdapter get() = _catatanAdapter!!
    private var isLinearLayout = true

    private val layoutDataStore: SettingsDataStore by lazy {
        SettingsDataStore(requireContext().dataStore)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentCatatanBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCatatanAdapter()
        layoutDataStore.preferenceFlow.asLiveData().observe(viewLifecycleOwner) {
            isLinearLayout = it
            setLayout()
            activity?.invalidateOptionsMenu()
        }
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
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

    private fun setLayout() {
        binding.recyclerView.layoutManager = if (isLinearLayout)
            LinearLayoutManager(context)
        else
            GridLayoutManager(context, 2)
    }

    private fun setIcon(menuItem: MenuItem) {
        val iconId = if (isLinearLayout)
            R.drawable.baseline_grid_view_24
        else
            R.drawable.baseline_view_list_24
        menuItem.icon = ContextCompat.getDrawable(requireContext(), iconId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.manu_main, menu)
        val menuItem = menu.findItem(R.id.action_switch_layout)
        setIcon(menuItem)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_catatanFragment_to_aboutFragment
            )
            return true
        } else if(item.itemId == R.id.action_switch_layout) {
            lifecycleScope.launch {
                layoutDataStore.saveLayout(!isLinearLayout, requireContext())
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initCatatanAdapter() {
        _catatanAdapter = CatatanAdapter()
        with(binding.recyclerView) {
            adapter = catatanAdapter
            setHasFixedSize(true)
        }
        viewModel.getAllCatatan.observe(viewLifecycleOwner) {
//            binding.tvJumlahMasuk.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            catatanAdapter.updateData(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        saldo = 0
        _catatanAdapter = null
    }

}