
package com.example.stockmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.stockmanager.data.MovimientoDao

class MovimientoViewModelFactory(
    private val movimientoDao: MovimientoDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(MovimientoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MovimientoViewModel(movimientoDao) as T
        }

        throw IllegalArgumentException("ViewModel desconocido")
    }
}
