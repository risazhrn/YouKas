package org.d3if3100.youkas.ui.catatan

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import org.d3if3100.youkas.databinding.FragmentAddCatatanBinding

class AddCatatanFragment : Fragment() {

    private lateinit var binding: FragmentAddCatatanBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddCatatanBinding.inflate(inflater, container, false)
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

        checkInput(jenis, nameNote, name, nominal, keterangan)

    }

    private fun checkInput(
        jenis: String,
        nameNote: String,
        name: String,
        nominal: String,
        keterangan: String,
    ) {

        if (TextUtils.isEmpty(jenis)){
            Toast.makeText(requireContext(), "Pilih jenis catatan", Toast.LENGTH_LONG).show()
        } else if(TextUtils.isEmpty(nameNote)) {
            Toast.makeText(requireContext(), "Masukan judul catatan", Toast.LENGTH_LONG).show()
        }else if(TextUtils.isEmpty(nominal)) {
            Toast.makeText(requireContext(), "Masukan Nominal Uang", Toast.LENGTH_LONG).show()
        }  else if(TextUtils.isEmpty(name)) {
            Toast.makeText(requireContext(), "Masukan Nama Lengkap", Toast.LENGTH_LONG).show()
        } else if (TextUtils.isEmpty(keterangan)) {
            Toast.makeText(requireContext(), "Masukan Keterangan", Toast.LENGTH_LONG).show()
        }

    }
}