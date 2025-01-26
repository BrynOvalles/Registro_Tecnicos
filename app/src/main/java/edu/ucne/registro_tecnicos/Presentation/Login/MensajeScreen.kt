package edu.ucne.registro_tecnicos.Presentation.Login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.ucne.registro_tecnicos.Presentation.Ticket.TicketViewModel

@Composable
fun MensajeScreen(
    tecnicoId: Int,
    ticketId: Int,
    createMensajes: () -> Unit,
    goTicket: () -> Unit,
    viewModel: TicketViewModel = hiltViewModel()
){
    var nombre by remember { mutableStateOf("") }
    val mensajes by remember { mutableStateOf(viewModel.getMensajes(ticketId)) }
    var replyText by remember { mutableStateOf("") }

    LaunchedEffect(tecnicoId) {
        if (tecnicoId > 0) {
            val tecnicoNombre = viewModel.getTecnicoNombre(tecnicoId)
            nombre = tecnicoNombre
        }
    }
    Scaffold {
        innerpadding ->
        Column(modifier = Modifier.padding(innerpadding)) {
            Text("By $nombre")
            Spacer(modifier = Modifier.height(8.dp))
            mensajes.forEach { mensaje ->
                Text(mensaje, style = MaterialTheme.typography.bodySmall)
                Spacer(modifier = Modifier.height(4.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Reply", style = MaterialTheme.typography.bodyMedium)
            BasicTextField(
                value = replyText,
                onValueChange = { replyText = it },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
            Row {
                Button(onClick = {
                    viewModel.addMensaje(ticketId, replyText)
                    replyText = ""
                }, modifier = Modifier.padding(top = 8.dp)) {
                    Text("New Message")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = goTicket, modifier = Modifier.padding(top = 8.dp)) {
                    Text("Go Back")
                }
            }
        }
    }
}

