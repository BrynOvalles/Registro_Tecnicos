package edu.ucne.registro_tecnicos.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registro_tecnicos.data.local.database.Data
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
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

}