package edu.ucne.registro_tecnicos.Presentation.Ticket

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_tecnicos.Presentation.Componentes.TopBar
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import java.text.SimpleDateFormat
import java.util.Locale

fun generateNewTicketId(): Int {
    return (System.currentTimeMillis() % 100000).toInt()
}

@Composable
fun TicketsScreen(
    viewModel: TicketViewModel = hiltViewModel(),
    goTicketsList: () -> Unit,
    ticketId: Int,
    createMensajes: () -> Unit
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    TicketsBodyScreen(
        ticketId,
        viewModel,
        uiState.value,
        goTicketsList,
        createMensajes
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsBodyScreen(
    ticketId: Int,
    viewModel: TicketViewModel,
    uiState: TicketUiState,
    goTicketsList: () -> Unit,
    createMensajes: () -> Unit
) {
    var errorMessage by remember { mutableStateOf("") }
    var expandPrioridad by remember { mutableStateOf(false) }
    val prioridad = listOf(1, 2, 3, 4, 5)
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    val calendar = Calendar.getInstance()
    val formatoFecha =
        remember { SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()) }
    val fechaFormateada =
        viewModel.uiState.value.fecha?.let { formatoFecha.format(it) } ?: ""
    var selectTecnico by remember { mutableStateOf<TecnicoEntity?>(null) }
    val tecnicosList by viewModel.tecnicosList.collectAsStateWithLifecycle()
    var expandTecnico by remember { mutableStateOf(false) }
    val tecnicoId = uiState.tecnicoId ?: 0

    LaunchedEffect(
        ticketId
    ) {
        if (ticketId > 0) {
            viewModel.find(ticketId)
        }
    }
    LaunchedEffect(tecnicoId) {
        if (tecnicoId > 0) {
            val tecnico = tecnicosList.find { it.tecnicosId == tecnicoId }
            selectTecnico = tecnico
        }
    }

    Scaffold(
        topBar = {
            TopBar(if (ticketId > 0) "Editar" else "Registrar")
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = uiState.cliente,
                        onValueChange = viewModel::onClienteChange,
                        label = { Text("Cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = uiState.asunto,
                        onValueChange = viewModel::onAsuntoChange,
                        label = { Text("Asunto") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (showDatePicker) {
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                viewModel.onFechaChange(formatoFecha.format(calendar.time))
                                showDatePicker = false
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    }
                    OutlinedTextField(
                        label = { Text("Fecha") },
                        value = fechaFormateada,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = "Seleccionar Fecha"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp)
                    )
                    OutlinedTextField(
                        value = uiState.descripcion,
                        onValueChange = viewModel::onDescripcionChange,
                        label = { Text("Descripcion") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandPrioridad,
                        onExpandedChange = { expandPrioridad = !expandPrioridad }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = uiState.prioridadId?.toString() ?: "Seleccionar Prioridad",
                            onValueChange = {},
                            label = { Text("Prioridad") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandPrioridad
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expandPrioridad,
                            onDismissRequest = { expandPrioridad = false }
                        ) {
                            prioridad.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.toString()) },
                                    onClick = {
                                        viewModel.onPrioridadChange(item)
                                        expandPrioridad = false
                                    }
                                )
                            }
                        }
                    }
                    ExposedDropdownMenuBox(
                        expanded = expandTecnico,
                        onExpandedChange = { expandTecnico = !expandTecnico }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = selectTecnico?.nombre ?: "Seleccionar Tecnico",
                            onValueChange = {},
                            label = { Text("Tecnico") },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth(),
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandTecnico
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expandTecnico,
                            onDismissRequest = { expandTecnico = false }
                        ) {
                            tecnicosList.forEach { tecnico ->
                                DropdownMenuItem(
                                    text = { Text(text = tecnico.nombre) },
                                    onClick = {
                                        selectTecnico = tecnico
                                        expandTecnico = false
                                        tecnico.tecnicosId?.let {
                                            viewModel.onTecnicoChange(it)
                                        }
                                    }
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                    errorMessage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (ticketId > 0) {
                                    viewModel.delete()
                                    goTicketsList()
                                }
                                else
                                    viewModel.new()
                            }
                        ) {
                            Icon(
                                imageVector = if (ticketId > 0) Icons.Default.Delete else Icons.Default.Refresh,
                                contentDescription = if (ticketId > 0) "Eliminar" else "Nuevo"
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = if (ticketId > 0) "Eliminar" else "Nuevo")
                        }

                        OutlinedButton(
                            onClick = {
                                viewModel.save()
                                goTicketsList()
                            }
                        ) {
                            Icon(
                                imageVector = if (ticketId > 0) Icons.Default.Refresh else Icons.Default.Check,
                                contentDescription = if (ticketId > 0) "Editar" else "Guardar"
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = if (ticketId > 0) "Editar" else "Guardar")
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            if (ticketId > 0){
                OutlinedButton(
                    onClick = {
                        createMensajes()
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Responder")
                }
            }
        }
    }
}
