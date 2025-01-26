package edu.ucne.registro_tecnicos.Presentation.Tecnico

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_tecnicos.Presentation.Componentes.TopBar
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import java.text.NumberFormat


@Composable
    fun TecnicosListScreen(
    viewModel: TecnicoViewModel = hiltViewModel(),
    createTecnicos: () -> Unit,
    goTecnicos: (Int) -> Unit
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        TecnicoListBodyScreen(
            uiState,
            createTecnicos,
            goTecnicos
        )
    }

@Composable
fun TecnicoListBodyScreen(
    uiState: TecnicoUiState,
    createTecnicos: () -> Unit,
    goTecnicos: (Int) -> Unit
) {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar ("Registrar")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { createTecnicos() }) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Tecnicos")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            Text("Listado de Tecnicos")

            HorizontalDivider()

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Id", modifier = Modifier.weight(1f), color = Color.Cyan)
                Text(text = "Nombre", modifier = Modifier.weight(1f), color = Color.Cyan)
                Text(text = "Sueldo", modifier = Modifier.weight(1f), color = Color.Cyan)
            }
            HorizontalDivider()
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ){
                items(uiState.tecnico){
                    TecnicosRow(
                        it,
                        goTecnicos
                    )
                }
            }
        }
    }
}

@Composable
private fun TecnicosRow(
    tecnico: TecnicoEntity,
    goTecnicos: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                goTecnicos(tecnico.tecnicosId!!)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium,
        border = CardDefaults.outlinedCardBorder()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(modifier = Modifier.weight(1f), text = tecnico.tecnicosId.toString(), style = MaterialTheme.typography.headlineMedium, color = Color.White)
            Text(
                modifier = Modifier.weight(1f),
                text = tecnico.nombre,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White
            )
            Text(modifier = Modifier.weight(1f), text = tecnico.sueldo, style = MaterialTheme.typography.headlineMedium, color = Color.White)
        }
        HorizontalDivider()
    }
}
