package org.d3if3100.youkas.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.d3if3100.youkas.databinding.ItemCatatanBinding
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.ui.catatan.CatatanFragmentDirections
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class CatatanAdapter: androidx.recyclerview.widget.ListAdapter<Catatan, CatatanAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Catatan>() {
                override fun areItemsTheSame(
                    oldItem: Catatan,
                    newItem: Catatan
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Catatan,
                    newItem: Catatan
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    class ViewHolder(private val binding: ItemCatatanBinding) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormatter = SimpleDateFormat("dd MMMM yyyy",
            Locale("id", "ID")
        )
        private val formatCurrency = NumberFormat.getCurrencyInstance(Locale("in", "ID"))


        fun bind(catatan: Catatan) = with(binding) {
            tvCatatanTanggal.text = dateFormatter.format(Date(catatan.created_at))
            tvCatatanKeterangan.text = catatan.keterangan
            when(catatan.jenis_catatan) {
                "Pemasukan" -> {
                    tvCatatanNominal.text = "+" + formatCurrency.format(catatan.nominal).toString()
                    tvCatatanNominal.setTextColor(Color.parseColor("#00ff00"))
                }
                "Pengeluaran" -> {
                    tvCatatanNominal.text = "-" + formatCurrency.format(catatan.nominal).toString()
                    tvCatatanNominal.setTextColor(Color.parseColor("#ff0000"))
                }
            }
            tvJenisCatatan.text = catatan.jenis_catatan
            tvCatatanPembuat.text = catatan.nama

            val direction =
                CatatanFragmentDirections.actionCatatanFragmentToDetailCatatanFragment(catatanId = catatan.id)
            root.setOnClickListener {
                it.findNavController()
                    .navigate(direction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCatatanBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatatanAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}