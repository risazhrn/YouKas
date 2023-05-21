package org.d3if3100.youkas.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3100.youkas.db.dao.CatatanDao
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.db.entity.Jumlah

class CatatanViewModel(private val db: CatatanDao): ViewModel() {
    var getAllCatatan: LiveData<List<Catatan>> = db.getAllKeuangan()

    fun tambahCatatan(catatan: Catatan) = viewModelScope.launch(Dispatchers.IO) {
        db.tambahCatatan(catatan)
    }

    fun getTotalByJenis(jenis_catatan:String): LiveData<Jumlah> {
        return db.getTotalByJenis(jenis_catatan)
    }

    fun getDetailCatatan(id: Long): LiveData<Catatan> = db.getDetailKeuangan(id)

    fun clearData() = viewModelScope.launch(Dispatchers.IO){
        db.clearData()
    }

    fun deleteCatatan(catatan: Catatan) = viewModelScope.launch(Dispatchers.IO) {
        db.deleteCatatan(catatan)
    }
}