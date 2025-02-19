package edu.ucne.registro_tecnicos.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import edu.ucne.registro_tecnicos.data.remote.RemoteDataSource
import edu.ucne.registro_tecnicos.data.remote.dto.PrioridadDto
import edu.ucne.registro_tecnicos.data.remote.dto.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject

class PrioridadRepository @Inject constructor(
        private val remoteDataSource: RemoteDataSource
    ) {
        fun getPrioridades(): Flow<Resource<List<PrioridadDto>>> = flow {
            try {
                emit(Resource.Loading())
                val prioridades = remoteDataSource.getPrioridades()
                emit(Resource.Success(prioridades))
            } catch (e: HttpException) {
                val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
                Log.e("PrioridadRep", "HttpException: $errorMessage")
                emit(Resource.Error("Error de conexion: $errorMessage"))
            } catch (e: Exception) {

                Log.e("PrioridadRep", "Exception: ${e.message}")
                emit(Resource.Error("Error: ${e.message}"))
            }
        }
        suspend fun update(id: Int, prioridadDto: PrioridadDto) = remoteDataSource.updatePrioridad(id, prioridadDto)
        suspend fun find(id: Int) = remoteDataSource.getPrioridad(id)
        suspend fun save(prioridadDto: PrioridadDto) = remoteDataSource.savePrioridad(prioridadDto)
        suspend fun delete(id: Int) = remoteDataSource.deletePrioridad(id)
}