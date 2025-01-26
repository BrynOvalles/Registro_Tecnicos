package edu.ucne.registro_tecnicos.data.repository

import edu.ucne.registro_tecnicos.data.local.dao.TecnicoDao
import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TecnicoRepository @Inject constructor (
    private val tecnicoDao: TecnicoDao
    ) {
        suspend fun saveTecnico(tecnico: TecnicoEntity) = tecnicoDao.save(tecnico)
        suspend fun find(id: Int) = tecnicoDao.find(id)
        suspend fun delete(tecnico: TecnicoEntity) = tecnicoDao.delete(tecnico)
        fun getAll() = tecnicoDao.getAll()

}