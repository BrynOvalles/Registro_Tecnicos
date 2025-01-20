package edu.ucne.registro_tecnicos.Presentation.Tecnico

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateNewTicketId(): Int {
    return (System.currentTimeMillis() % 100000).toInt()  // Ejemplo simple
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsScreen(
    ticketsRepository: TicketRepository,
    goTicketsList: () -> Unit,
    tecnicosList: List<TecnicoEntity>,
    ticketId: Int
) {
    var fecha by remember { mutableStateOf("") }
    var prioridadId by remember { mutableIntStateOf(0) }
    var cliente by remember { mutableStateOf("") }
    var asunto by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var selectPrioridad by remember { mutableStateOf("") }
    var selectTecnico by remember { mutableStateOf<TecnicoEntity?>(null) }
    var errorMessage by remember { mutableStateOf("") }
    var expandPrioridad by remember { mutableStateOf(false) }
    var expandTecnico by remember { mutableStateOf(false) }
    val prioridad = listOf("Sin Prioridad","Alta", "Media", "Baja")
    val calendario = remember { Calendar.getInstance() }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(
        ticketId
    ) {
        if (ticketId > 0) {
            val ticket = ticketsRepository.find(ticketId)
            ticket?.let {
                fecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it.fecha)
                prioridadId = it.prioridadId ?: 0
                cliente = it.cliente
                selectPrioridad = prioridad.getOrElse(prioridadId) { "" }
                asunto = it.asunto
                descripcion = it.descripcion
                selectTecnico = tecnicosList.find { tecnico ->
                    tecnico.tecnicosId == it.tecnicoId
                }
            }
        }
    }

    Scaffold { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerpadding)
                .padding(8.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = cliente,
                        onValueChange = { cliente = it },
                        label = { Text("Cliente") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = asunto,
                        onValueChange = { asunto = it },
                        label = { Text("Asunto") },
                        modifier = Modifier.fillMaxWidth()
                        )
                    OutlinedButton(
                        onClick = {
                            var datePicker = DatePickerDialog(
                                context,
                                { _, year, month, dayOfMonth ->
                                    calendario.set(year, month, dayOfMonth)
                                    val fechaFormateada =
                                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                            .format(calendario.time)
                                    fecha = fechaFormateada
                                },
                                calendario.get(Calendar.YEAR),
                                calendario.get(Calendar.MONTH),
                                calendario.get(Calendar.DAY_OF_MONTH)
                            )
                            datePicker.show()
                        },
                        modifier = Modifier
                            .size(200.dp, 50.dp)
                            .padding(vertical = 8.dp)
                    ){
                        Text(text = if (fecha.isEmpty()) "Fecha" else fecha)
                    }
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripcion") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandPrioridad,
                        onExpandedChange = { expandPrioridad = !expandPrioridad }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = if (selectPrioridad.isEmpty()) "Selecciona Una Prioridad" else selectPrioridad,
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
                        ){
                            prioridad.forEachIndexed { index, item ->
                                DropdownMenuItem(
                                    text = { Text(text = item) },
                                    onClick = {
                                        prioridadId = index
                                        selectPrioridad = item
                                        expandPrioridad = false
                                    }
                                )
                            }
                        }
                    }

                    ExposedDropdownMenuBox(
                        expanded = expandTecnico,
                        onExpandedChange = { expandTecnico = !expandTecnico }
                    ){
                        OutlinedTextField(
                            readOnly = true,
                            value = selectTecnico?.nombre ?: "Selecciona Un Tecnico",
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
                        ){
                            tecnicosList.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it.nombre) },
                                    onClick = {
                                        selectTecnico = it
                                        expandTecnico = false
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
                                cliente = ""
                                asunto = ""
                                descripcion = ""
                                fecha = ""
                                prioridadId = 0
                                selectTecnico = null
                                errorMessage = ""
                            }
                        )  {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Refresh,
                                contentDescription = "Nuevo"
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Nuevo")
                        }

                        OutlinedButton(
                            onClick = {

                                if (cliente.isEmpty() || asunto.isEmpty() || descripcion.isEmpty() ||
                                    fecha.isEmpty() || selectTecnico == null
                                )
                                {
                                    errorMessage = "Campos Obligatorios"
                                    return@OutlinedButton
                                }

                                val fechaFormateada = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                                    .parse(fecha)
                                scope.launch {
                                    ticketsRepository.save(
                                        TicketEntity(
                                            ticketId = if (ticketId > 0) ticketId else generateNewTicketId(),
                                            cliente = cliente,
                                            asunto = asunto,
                                            descripcion = descripcion,
                                            fecha = fechaFormateada?: Date(),
                                            prioridadId = prioridadId,
                                            tecnicoId = selectTecnico!!.tecnicosId
                                        )
                                    )
                                    goTicketsList()
                                }
                            }
                        ){
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.Check,
                                contentDescription = "Guardar"
                            )
                            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                            Text(text = if (ticketId > 0) "Editar" else "Guardar")
                        }
                        if (ticketId > 0) {
                            OutlinedButton(
                                onClick = {
                                    scope.launch {
                                        ticketsRepository.delete(
                                            TicketEntity(
                                                ticketId = ticketId,
                                                cliente = "",
                                                asunto = "",
                                                descripcion = "",
                                                fecha = Date(),
                                                prioridadId = 0,
                                                tecnicoId = 0,
                                            )
                                        )
                                        cliente = ""
                                        asunto = ""
                                        descripcion = ""
                                        fecha = ""
                                        prioridadId = 0
                                        selectTecnico = null
                                        goTicketsList()
                                    }
                                }
                            ){
                                Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                                    contentDescription = "Eliminar",
                                    tint = Color.Red,
                                )
                                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                                Text(text = "Borrar", color = Color.Red)
                            }
                        }
                    }
                }
            }
        }
    }
}