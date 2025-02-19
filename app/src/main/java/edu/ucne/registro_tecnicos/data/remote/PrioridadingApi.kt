package edu.ucne.registro_tecnicos.data.remote

import edu.ucne.registro_tecnicos.data.remote.dto.PrioridadDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PrioridadingApi {
    @GET("api/Prioridad")
    suspend fun getPrioridades(): List<PrioridadDto>

    @GET("api/Prioridad/{id}")
    suspend fun getPrioridad(@Path("id") id: Int): PrioridadDto

    @POST("api/Prioridad")
    suspend fun savePrioridad(@Body prioridadDto: PrioridadDto): Response<PrioridadDto>

    @PUT("api/Prioridad/{id}")
    suspend fun updatePrioridad(@Path("id") id: Int, @Body prioridadDto: PrioridadDto): Response<PrioridadDto>

    @DELETE("api/Prioridad/{id}")
    suspend fun deletePrioridad(@Path("id") id: Int): Response<Unit>
}