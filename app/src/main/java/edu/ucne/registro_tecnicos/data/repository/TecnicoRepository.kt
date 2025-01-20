package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import kotlinx.coroutines.flow.Flow

class TecnicoRepository (
    private val tecnicoDb: Data
    ) {
        suspend fun saveTecnico(tecnico: TecnicoEntity) {
            tecnicoDb.tecnicoDao().save(tecnico)
        }
        suspend fun find(id: Int): TecnicoEntity?{
            return tecnicoDb.tecnicoDao().find(id)
        }
        suspend fun delete(tecnico: TecnicoEntity){
            return tecnicoDb.tecnicoDao().delete(tecnico)
        }
        fun getAll(): Flow<List<TecnicoEntity>> {
            return tecnicoDb.tecnicoDao().getAll()
        }
}