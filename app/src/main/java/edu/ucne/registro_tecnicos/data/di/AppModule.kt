package edu.ucne.registro_tecnicos.data.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.remote.PrioridadingApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    const val BASE_URL = "https://apiprioridad-adbjbmasgwehbhg5.eastus-01.azurewebsites.net/"

    @Provides
    @Singleton
    fun provideTecnicosRepository( @ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            Data::class.java,
            "Data.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideTecnicoDao( appDataDb: Data ) = appDataDb.ticketDao()

    @Provides
    @Singleton
    fun provideTicketDao ( appDataDb: Data ) = appDataDb.tecnicoDao()

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun providesTicketManagerApi(moshi: Moshi): PrioridadingApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(PrioridadingApi::class.java)
    }

}