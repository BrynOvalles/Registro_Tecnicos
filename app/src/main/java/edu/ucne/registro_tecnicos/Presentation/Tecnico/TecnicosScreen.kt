package edu.ucne.registro_tecnicos.Presentation.Tecnico
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import edu.ucne.registro_tecnicos.Presentation.Componentes.TopBar

@Composable
    fun TecnicosScreen(
    tecnicoId: Int,
    goTecnicosList : () -> Unit,
    viewModel: TecnicoViewModel = hiltViewModel()
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        TecnicoBodyScreen(
            tecnicoId = tecnicoId,
            viewModel,
            uiState = uiState,
            goTecnicosList
        )
    }

@Composable
fun TecnicoBodyScreen(
    tecnicoId: Int,
    viewModel: TecnicoViewModel,
    uiState: TecnicoUiState,
    goTecnicosList: () -> Unit
) {
    LaunchedEffect(tecnicoId) {
        if (tecnicoId>0) viewModel.find(tecnicoId)
    }
    Scaffold (
        topBar = {
            TopBar(if (tecnicoId>0) "Editar" else "Registrar")
        }
    ){ innerPadding ->

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
                        value = uiState.nombre,
                        onValueChange = viewModel::onNombreChange,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        label = { Text("Sueldo") },
                        value = if (uiState.sueldo == null) "" else uiState.sueldo.toString(),
                        onValueChange = {
                            val sueldo = it.toIntOrNull()
                            if (sueldo != null) {
                                viewModel.onSueldoChange(sueldo)
                            }
                            else {
                                viewModel.onSueldoChange(0)
                            }
                        },
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    uiState.errormessaage?.let {
                        Text(text = it, color = Color.Red)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (tecnicoId != null) {
                                    viewModel.new()
                                }
                                else {
                                    viewModel.delete()
                                    goTecnicosList()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (tecnicoId != null) Icons.Default.Refresh else Icons.Default.Delete,
                                contentDescription = if (tecnicoId != null) "Refresh button" else "Delete button"
                            )
                            Text(if (tecnicoId != null) "Limpiar" else "Eliminar")
                        }
                        OutlinedButton(
                            onClick = {
                                    viewModel.save()
                                    goTecnicosList()
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
