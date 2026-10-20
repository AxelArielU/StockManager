

package com.example.stockmanager.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.stockmanager.data.Movimiento
import com.example.stockmanager.ui.viewmodel.MovimientoViewModel

@Composable
fun InventarioScreen(viewModel: MovimientoViewModel) {

    var codigo by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("Entrada") }
    var fecha by remember { mutableStateOf("") }

    var movimientoEditandoId by remember {
        mutableStateOf<Int?>(null)
    }

    val movimientos by viewModel.movimientos.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "StockManager",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Gestión de inventario offline",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código del producto") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = cantidad,
                onValueChange = {
                    cantidad = it.filter { c -> c.isDigit() }
                },
                label = { Text("Cantidad") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true
            )

            Text(
                text = "Tipo de movimiento",
                style = MaterialTheme.typography.titleSmall
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { tipo = "Entrada" }
                ) {
                    Text(
                        if (tipo == "Entrada") {
                            "✓ Entrada"
                        } else {
                            "Entrada"
                        }
                    )
                }

                Button(
                    onClick = { tipo = "Salida" }
                ) {
                    Text(
                        if (tipo == "Salida") {
                            "✓ Salida"
                        } else {
                            "Salida"
                        }
                    )
                }
            }

            OutlinedTextField(
                value = fecha,
                onValueChange = { fecha = it },
                label = { Text("Fecha (DD/MM/AAAA)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val cantidadNumero = cantidad.toIntOrNull()

                    if (
                        codigo.isNotBlank() &&
                        cantidadNumero != null &&
                        cantidadNumero > 0 &&
                        fecha.isNotBlank()
                    ) {
                        val movimiento = Movimiento(
                            id = movimientoEditandoId ?: 0,
                            codigoProducto = codigo.trim(),
                            cantidad = cantidadNumero,
                            tipoMovimiento = tipo,
                            fecha = fecha.trim(),
                            sincronizado = false
                        )

                        if (movimientoEditandoId == null) {
                            viewModel.insertar(movimiento)
                        } else {
                            viewModel.actualizar(movimiento)
                        }

                        codigo = ""
                        cantidad = ""
                        fecha = ""
                        tipo = "Entrada"
                        movimientoEditandoId = null
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    if (movimientoEditandoId == null) {
                        "Registrar movimiento"
                    } else {
                        "Guardar cambios"
                    }
                )
            }

            if (movimientoEditandoId != null) {
                Button(
                    onClick = {
                        codigo = ""
                        cantidad = ""
                        fecha = ""
                        tipo = "Entrada"
                        movimientoEditandoId = null
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancelar edición")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Movimientos registrados: ${movimientos.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = movimientos,
                    key = { it.id }
                ) { movimiento ->

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {

                            Text(
                                text = "Producto: ${movimiento.codigoProducto}"
                            )

                            Text(
                                text = "Cantidad: ${movimiento.cantidad}"
                            )

                            Text(
                                text = "Tipo: ${movimiento.tipoMovimiento}"
                            )

                            Text(
                                text = "Fecha: ${movimiento.fecha}"
                            )

                            Text(
                                text = if (movimiento.sincronizado) {
                                    "Sincronizado"
                                } else {
                                    "Pendiente de sincronización"
                                }
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Row(
                                horizontalArrangement =
                                    Arrangement.spacedBy(8.dp)
                            ) {

                                Button(
                                    onClick = {
                                        movimientoEditandoId =
                                            movimiento.id

                                        codigo =
                                            movimiento.codigoProducto

                                        cantidad =
                                            movimiento.cantidad.toString()

                                        tipo =
                                            movimiento.tipoMovimiento

                                        fecha =
                                            movimiento.fecha
                                    }
                                ) {
                                    Text("Editar")
                                }

                                Button(
                                    onClick = {
                                        viewModel.eliminar(movimiento)

                                        if (
                                            movimientoEditandoId ==
                                            movimiento.id
                                        ) {
                                            codigo = ""
                                            cantidad = ""
                                            fecha = ""
                                            tipo = "Entrada"
                                            movimientoEditandoId = null
                                        }
                                    }
                                ) {
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
