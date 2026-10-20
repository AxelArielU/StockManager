
package com.example.stockmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockmanager.data.StockDatabase
import com.example.stockmanager.ui.InventarioScreen
import com.example.stockmanager.ui.viewmodel.MovimientoViewModel
import com.example.stockmanager.ui.viewmodel.MovimientoViewModelFactory
import com.example.stockmanager.ui.theme.StockManagerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = StockDatabase.obtenerBaseDeDatos(applicationContext)

        setContent {
            StockManagerTheme {
                val movimientoViewModel: MovimientoViewModel = viewModel(
                    factory = MovimientoViewModelFactory(
                        database.movimientoDao()
                    )
                )

                InventarioScreen(
                    viewModel = movimientoViewModel,
                )
            }
        }
    }
}
