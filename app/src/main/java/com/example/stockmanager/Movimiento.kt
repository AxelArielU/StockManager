
package com.example.stockmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movimientos")
data class Movimiento(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val codigoProducto: String,

    val cantidad: Int,

    val tipoMovimiento: String,

    val fecha: String,

    val sincronizado: Boolean = false

)
