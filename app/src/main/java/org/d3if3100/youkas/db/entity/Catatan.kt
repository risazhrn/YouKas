package org.d3if3100.youkas.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Catatan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val keterangan: String,
    val jenis_catatan: String,
    val nominal: Float,
    val judul_catatan: String,
    val created_at: Long = System.currentTimeMillis(),
)