package edu.ucne.registro_tecnicos.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Tecnicos")
    data class TecnicoEntity(
        @PrimaryKey
        val tecnicosId: Int? = null,
        val nombre: String = "",
        val sueldo: String = ""
)