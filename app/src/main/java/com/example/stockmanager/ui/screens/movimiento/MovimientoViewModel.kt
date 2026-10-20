
package com.example.stockmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanager.data.Movimiento
import com.example.stockmanager.data.MovimientoDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MovimientoViewModel(
    private val movimientoDao: MovimientoDao
) : ViewModel() {

    val movimientos = movimientoDao.obtenerTodos().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertar(movimiento: Movimiento) {
        viewModelScope.launch {
            movimientoDao.insertar(movimiento)
        }
    }

    fun actualizar(movimiento: Movimiento) {
        viewModelScope.launch {
            movimientoDao.actualizar(movimiento)
        }
    }

    fun eliminar(movimiento: Movimiento) {
        viewModelScope.launch {
            movimientoDao.eliminar(movimiento)
        }
    }
}
