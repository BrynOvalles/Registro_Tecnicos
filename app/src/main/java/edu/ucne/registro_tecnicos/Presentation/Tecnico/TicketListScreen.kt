package edu.ucne.registro_tecnicos.Presentation.Tecnico

import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TicketsListScreen(
    ticketsList: List<TicketEntity>,
    createTickets: () -> Unit,
    goTickets: (Int) -> Unit
    ) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title= {
                    Text("Listado de Ticket",
                     color = Color.Cyan)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { createTickets() }) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Ticket")
            }
        }
    ) { innerPadding ->
    Column (
        modifier = Modifier.fillMaxSize().padding(innerPadding)
    ) {
        Spacer (modifier = Modifier.height(42.dp))
        Cabecera()
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(ticketsList) {
                TicketRow(it, goTickets)
            }
        }
    }
    }
}

@Composable
private fun Cabecera() {
    Text("Listado de Tickets")
    HorizontalDivider()
    Row(
        modifier = Modifier.fillMaxWidth().padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
//        Text(
//            modifier = Modifier.weight(0.2f),
//            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
//            text = "Id"
//        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Fecha",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Prioridad",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Cliente",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Asunto",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Descripcion",
            textAlign = TextAlign.Start
        )
        Text(
            modifier = Modifier.weight(0.2f),
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.ExtraLight),
            text = "Tecnico",
            textAlign = TextAlign.Start
        )
    }
    HorizontalDivider()
}

@Composable
fun TicketRow(
    ticket: TicketEntity,
    goTicket: (Int) -> Unit
) {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val fechaFormateada = formatoFecha.format(ticket.fecha)

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
            .clickable {
                goTicket((ticket.ticketId)?:0)
            }
    ) {
//        Text(
//            modifier = Modifier.weight(1f), text = ticket.ticketId.toString()
//        )
        Text(
            modifier = Modifier.weight(1f),
            text = fechaFormateada
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.prioridadId.toString()
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.cliente
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.asunto
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.descripcion
        )
        Text(
            modifier = Modifier.weight(1f),
            text = ticket.tecnicoId.toString()
        )

    }
    HorizontalDivider()
}