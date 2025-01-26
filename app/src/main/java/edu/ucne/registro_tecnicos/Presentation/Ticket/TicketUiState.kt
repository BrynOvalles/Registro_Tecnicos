package edu.ucne.registro_tecnicos.Presentation.Ticket

import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import java.util.Date

data class TicketUiState(
    val ticketId: Int? = null,
    val fecha: Date? = null,
    val prioridadId: Int? = null,
    val cliente: String = "",
    val asunto: String = "",
    val descripcion: String = "",
    val tecnicoId: Int? = null,
    val errorMessage: String? = null,
    val tickets: List<TicketEntity> = emptyList()
)