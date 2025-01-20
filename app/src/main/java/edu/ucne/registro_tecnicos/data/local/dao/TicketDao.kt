package edu.ucne.registro_tecnicos.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import kotlinx.coroutines.flow.Flow

@Dao
    interface TicketDao {
        @Upsert()
        suspend fun save(ticket: TicketEntity)

        @Query(
            """
            SELECT *
            FROM ticket
            WHERE ticketId=:id
            LIMIT 1
        """
        )
        suspend fun find(id: Int): TicketEntity?

        @Delete
        suspend fun delete(ticket: TicketEntity)

        @Query("SELECT * FROM Ticket")
        fun getAll(): Flow<List<TicketEntity>>
    }