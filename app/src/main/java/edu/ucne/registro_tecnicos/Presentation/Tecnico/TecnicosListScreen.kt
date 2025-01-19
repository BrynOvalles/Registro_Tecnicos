package edu.ucne.registro_tecnicos.Presentation.Tecnico

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import java.text.NumberFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
    fun TecnicosListScreen(
    tecnicosList: List<TecnicoEntity>,
    createTecnicos: () -> Unit,
    ) {
        Scaffold (
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CenterAlignedTopAppBar(
                    title= {
                        Text("Listado de Tecnicos")
                        Color.Cyan
                    }
                )
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
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ){
                    items(tecnicosList){
                        TecnicosRow(it)
                    }
                }
                }
            }
        }

@Composable
private fun TecnicosRow(tecnico: TecnicoEntity) {
    val formato = "RD ${NumberFormat.getNumberInstance().format(tecnico.sueldo)}"
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = tecnico.tecnicosId.toString())
        Text(
            modifier = Modifier.weight(1f),
            text = tecnico.nombre
        )
        Text(modifier = Modifier.weight(1f), text = formato)
    }
    HorizontalDivider()
}
