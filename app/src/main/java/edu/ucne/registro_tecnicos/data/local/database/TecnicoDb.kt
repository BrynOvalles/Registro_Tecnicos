package edu.ucne.registro_tecnicos.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registro_tecnicos.data.local.dao.TecnicoDao
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity

@Database(
        entities = [TecnicoEntity::class],
        version = 1,
        exportSchema = false
    )
    abstract class TecnicoDb : RoomDatabase() {
        abstract fun tecnicoDao(): TecnicoDao
    }