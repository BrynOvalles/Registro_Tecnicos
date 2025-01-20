package edu.ucne.registro_tecnicos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Ticket")
    data class TicketEntity(
        @PrimaryKey
        val ticketId: Int? = null,
        val fecha: Date,
        val prioridadId: Int? = null,
        val cliente: String = "",
        val asunto: String = "",
        val descripcion: String = "",
        val tecnicoId: Int? = null
)