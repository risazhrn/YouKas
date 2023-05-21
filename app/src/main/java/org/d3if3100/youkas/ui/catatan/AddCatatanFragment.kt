package org.d3if3100.youkas.ui.catatan

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3100.youkas.R
import org.d3if3100.youkas.databinding.FragmentAddCatatanBinding
import org.d3if3100.youkas.db.RoomDB
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.model.CatatanViewModel
import org.d3if3100.youkas.model.factory.CatatanViewModelFactory

class AddCatatanFragment : Fragment() {

    private var _binding: FragmentAddCatatanBinding? = null
    private val viewModel: CatatanViewModel by lazy {
        val db = RoomDB.getInstance(requireContext())
        val factory = CatatanViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[CatatanViewModel::class.java]
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddCatatanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnSimpan.setOnClickListener {
            tambahCatatan()
        }
    }

    private fun tambahCatatan() {
        val jenis = binding.spinnerJenisNote.selectedItem.toString()
        val nameNote = binding.edtNameNote.text.toString()
        val name = binding.edtName.text.toString()
        val nominal = binding.edtNonimal.text.toString()
        val keterangan = binding.edtKeterangan.text.toString()

        if (checkInput(jenis, nameNote, name, nominal, keterangan)) return

        val catatan = Catatan(
            0L,
            name,
            keterangan,
            jenis,
            nominal.toFloat(),
            nameNote,
            System.currentTimeMillis()
        )

        viewModel.tambahCatatan(catatan)
        Toast.makeText(requireContext(), "Keuangan berhasil ditambahkan", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addCatatanFragment_to_catatanFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkInput(
        jenis: String,
        nameNote: String,
        name: String,
        nominal: String,
        keterangan: String,
    ): Boolean {
        if (TextUtils.isEmpty(jenis)) {
            Toast.makeText(requireContext(), "Pilih jenis catatan", Toast.LENGTH_LONG).show()
            return true
        } else if (TextUtils.isEmpty(nameNote)) {
            Toast.makeText(requireContext(), "Masukan judul catatan", Toast.LENGTH_LONG).show()
            return true
        } else if (TextUtils.isEmpty(nominal)) {
            Toast.makeText(requireContext(), "Masukan Nominal Uang", Toast.LENGTH_LONG).show()
            return true
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), "Masukan Nama Lengkap", Toast.LENGTH_LONG).show()
            return true
        } else if (TextUtils.isEmpty(keterangan)) {
            Toast.makeText(requireContext(), "Masukan Keterangan", Toast.LENGTH_LONG).show()
            return true
        }
        return false
    }
}