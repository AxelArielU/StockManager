
package com.example.stockmanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Movimiento::class],
    version = 1,
    exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {

    abstract fun movimientoDao(): MovimientoDao

    companion object {

        @Volatile
        private var INSTANCE: StockDatabase? = null

        fun obtenerBaseDeDatos(context: Context): StockDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    StockDatabase::class.java,
                    "stockmanager_db"
                ).build()

                INSTANCE = instancia
                instancia
            }
        }
    }
}
