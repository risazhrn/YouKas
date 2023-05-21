package org.d3if3100.youkas.model.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3100.youkas.db.dao.CatatanDao
import org.d3if3100.youkas.model.CatatanViewModel

class CatatanViewModelFactory (
    private val db: CatatanDao
    ) : ViewModelProvider.Factory
    {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CatatanViewModel::class.java)) {
                return CatatanViewModel(db) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }