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
import edu.ucne.registro_tecnicos.data.local.database.TecnicoDb
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme


class MainActivity : ComponentActivity() {
    private lateinit var tecnicoRepository: TecnicoRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val tecnicoDb = Room.databaseBuilder(
            applicationContext,
            TecnicoDb::class.java,
            "Tecnicos.db"
        ).fallbackToDestructiveMigration()
            .build()
        tecnicoRepository = TecnicoRepository(tecnicoDb)

        setContent {
            Registro_TecnicosTheme {
                Text(
                    text = "Tecnicos",
                    modifier = Modifier.padding(16.dp)
                )
                TecnicosNavHost(tecnicoRepository, rememberNavController()
                )
            }
        }
    }

//    private suspend fun saveTecnico(tecnico: TecnicoEntity) {
//        tecnicoDb.tecnicoDao().save(tecnico)
//    }
}