package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

class TicketRepository (
    private val ticketDb: Data
    ) {
        suspend fun save(ticket: TicketEntity) {
            ticketDb.ticketDao().save(ticket)
        }
        suspend fun find(id: Int): TicketEntity?{
            return ticketDb.ticketDao().find(id)
        }
        suspend fun delete(tecnico: TicketEntity){
            return ticketDb.ticketDao().delete(tecnico)
        }
        fun getAll(): Flow<List<TicketEntity>> {
            return ticketDb.ticketDao().getAll()
        }
}