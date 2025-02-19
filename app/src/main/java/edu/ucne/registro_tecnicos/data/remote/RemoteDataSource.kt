package edu.ucne.registro_tecnicos.data.remote

import edu.ucne.registro_tecnicos.data.remote.dto.PrioridadDto
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val prioridadingApi: PrioridadingApi
) {
    suspend fun getPrioridad(id: Int) = prioridadingApi.getPrioridad(id)
    suspend fun getPrioridades() = prioridadingApi.getPrioridades()
    suspend fun savePrioridad(prioridadDto: PrioridadDto) = prioridadingApi.savePrioridad(prioridadDto)
    suspend fun updatePrioridad(id: Int, prioridadDto: PrioridadDto) = prioridadingApi.updatePrioridad(id, prioridadDto)
    suspend fun deletePrioridad(id: Int) = prioridadingApi.deletePrioridad(id)

}