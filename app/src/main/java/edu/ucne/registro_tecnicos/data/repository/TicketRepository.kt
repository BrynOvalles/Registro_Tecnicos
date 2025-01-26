package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.dao.TicketDao
import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TicketRepository @Inject constructor (
    private val ticketDao: TicketDao
    ) {
        suspend fun save(ticket: TicketEntity) = ticketDao.save(ticket)
        suspend fun find(id: Int) = ticketDao.find(id)
        suspend fun delete(ticket: TicketEntity) = ticketDao.delete(ticket)
        fun getAll()= ticketDao.getAll()
}