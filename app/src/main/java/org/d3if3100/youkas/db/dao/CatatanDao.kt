package org.d3if3100.youkas.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.db.entity.Jumlah

@Dao
interface CatatanDao {
    @Insert
    fun tambahCatatan(catatan: Catatan)

    @Query("SELECT * FROM catatan ORDER BY created_at DESC")
    fun getAllKeuangan(): LiveData<List<Catatan>>

    @Query("SELECT SUM(nominal) jumlah FROM catatan WHERE jenis_catatan = :jenis LIMIT 1")
    fun getTotalByJenis(jenis: String): LiveData<Jumlah>

    @Query("SELECT * FROM catatan WHERE id = :id LIMIT 1")
    fun getDetailKeuangan(id: Long): LiveData<Catatan>

    @Query("DELETE FROM catatan")
    fun clearData()

    @Delete
    fun deleteCatatan(catatan: Catatan)
}