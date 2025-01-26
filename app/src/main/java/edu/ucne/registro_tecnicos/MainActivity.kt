package edu.ucne.registro_tecnicos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registro_tecnicos.Presentation.Navigation.GlobalNavHost
import edu.ucne.registro_tecnicos.ui.theme.Registro_TecnicosTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Registro_TecnicosTheme {
                val navHost = rememberNavController()
                GlobalNavHost(navHost)
            }
        }
    }
}