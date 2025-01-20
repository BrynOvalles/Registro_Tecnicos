package edu.ucne.registro_tecnicos.Presentation.Tecnico
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import kotlinx.coroutines.launch

@Composable
    fun TecnicosScreen(
    tecnicoRepository: TecnicoRepository,
    goTecnicosList: () -> Unit
    ) {
        var nombre by remember { mutableStateOf("") }
        var sueldo by remember { mutableStateOf("") }
        var errormessaage: String? by remember { mutableStateOf(null) }

        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            label = { Text("Nombre") },
                            value = nombre,
                            onValueChange = { nombre = it },
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            label = { Text("Sueldo") },
                            value = sueldo,
                            onValueChange = { sueldo = it },
                            maxLines = 1,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                        errormessaage?.let {
                            Text(text = it, color = Color.Red)
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            OutlinedButton(
                                onClick = {
                                    nombre = ""
                                    sueldo = ""
                                    errormessaage = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "new button"
                                )
                                Text("Nuevo")
                            }
                            val scope = rememberCoroutineScope()
                            OutlinedButton(
                                onClick = {
                                    if (nombre.isBlank() || sueldo.isBlank()) {
                                        errormessaage =
                                            "El nombre o el sueldo no pueden estar vacio"
                                    }
                                    else scope.launch {
                                        tecnicoRepository.saveTecnico(
                                            TecnicoEntity(
                                                nombre = nombre,
                                                sueldo = sueldo.toInt()
                                            )
                                        )
                                        nombre = ""
                                        sueldo = ""
                                        errormessaage = ""
                                        goTecnicosList()
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "save button"
                                )
                                Text(text = "Guardar")
                            }
                        }
                    }
                }
            }
        }
    }