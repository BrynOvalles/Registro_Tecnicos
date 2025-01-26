package edu.ucne.registro_tecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.ucne.registro_tecnicos.data.local.dao.TecnicoDao
import edu.ucne.registro_tecnicos.data.local.dao.TicketDao
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity

@Database(
        entities = [TecnicoEntity::class, TicketEntity::class],
        version = 4,
        exportSchema = false
    )
@TypeConverters(Converters::class)
    abstract class Data : RoomDatabase() {
        abstract fun tecnicoDao(): TecnicoDao
        abstract fun ticketDao(): TicketDao
    }

