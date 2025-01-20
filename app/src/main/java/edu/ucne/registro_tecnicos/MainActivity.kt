package edu.ucne.registro_tecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import edu.ucne.registro_tecnicos.Presentation.Navigation.TecnicosNavHost
import edu.ucne.registro_tecnicos.data.local.database.Data
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme


class MainActivity : ComponentActivity() {
    private lateinit var tecnicoRepository: TecnicoRepository
    private lateinit var ticketsRepository: TicketRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val dataDb = Room.databaseBuilder(
            applicationContext,
            Data::class.java,
            "Tecnicos.db"
        ).fallbackToDestructiveMigration()
            .build()
        tecnicoRepository = TecnicoRepository(dataDb)
        ticketsRepository = TicketRepository(dataDb)

        setContent {
            Registro_TecnicosTheme {
                Text(
                    text = "Tecnicos",
                    modifier = Modifier.padding(16.dp)
                )
                TecnicosNavHost(tecnicoRepository, ticketsRepository, rememberNavController()
                )
            }
        }
    }
}