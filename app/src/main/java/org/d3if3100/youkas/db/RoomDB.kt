package org.d3if3100.youkas.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3100.youkas.db.entity.Catatan
import org.d3if3100.youkas.db.dao.CatatanDao

@Database(entities = [Catatan::class], version = 3, exportSchema = false)
abstract class RoomDB : RoomDatabase() {
    abstract val dao: CatatanDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        fun getInstance(context: Context): RoomDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java,
                        "catatan.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }

        }

    }
}