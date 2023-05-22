package org.d3if3100.youkas.ui.catatan

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.FragmentDetailCatatanBinding
import org.d3if3100.youkas.db.RoomDB
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.model.CatatanViewModel
import org.d3if3100.youkas.model.factory.CatatanViewModelFactory
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class DetailCatatanFragment : Fragment() {

    private var catatanId: Long = -1
    private var _binding: FragmentDetailCatatanBinding? = null
    private lateinit var catatan: Catatan
    private val args: DetailCatatanFragmentArgs by navArgs()
    private val viewModel: CatatanViewModel by lazy {
        val db = RoomDB.getInstance(requireContext())
        val factory = CatatanViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[CatatanViewModel::class.java]
    }

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catatanId = args.catatanId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailCatatanBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        binding.btnShare.setOnClickListener {
            shareKeuangan()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() {
        viewModel.getDetailCatatan(catatanId).observe(requireActivity()) {
            if (it == null) return@observe
            val dateFormatter = SimpleDateFormat(
                "dd MMMM yyyy",
                Locale("id", "ID")
            )
            val formatCurrency = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

            catatan = it
            binding.tvDetailName.text = "Nama Lengkap : " + it.nama
            binding.tvDetailKeuanganTanggal.text = "Tanggal : " + dateFormatter.format(Date(it.created_at))
            binding.tvDetailKeterangan.text = "Keterangan : " + it.keterangan
            binding.tvDetailJenisNote.text = it.jenis_catatan
            when (it.jenis_catatan) {
                "Pemasukan" -> {
                    binding.tvDetailNominal.text = "+" + formatCurrency.format(catatan.nominal).toString()
                    binding.tvDetailNominal.setTextColor(Color.parseColor("#228b22"))
                }
                "Pengeluaran" -> {
                    binding.tvDetailNominal.text = "-" + formatCurrency.format(catatan.nominal).toString()
                    binding.tvDetailNominal.setTextColor(Color.parseColor("#ff0000"))
                }
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun shareKeuangan() {
        val formatCurrency = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

        val message = getString(
            R.string.bagikan_template,
            catatan.nama,
            catatan.jenis_catatan,
            formatCurrency.format(catatan.nominal),
            catatan.keterangan
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            hapusData()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.deleteCatatan(catatan)
                findNavController().navigate(R.id.action_detailCatatanFragment_to_catatanFragment)
            }
            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}