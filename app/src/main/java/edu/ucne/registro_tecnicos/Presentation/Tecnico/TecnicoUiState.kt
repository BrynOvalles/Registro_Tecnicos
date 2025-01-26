package edu.ucne.registro_tecnicos.Presentation.Tecnico

import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity

data class TecnicoUiState(
    val tecnicoId: Int? = null,
    val nombre: String = "",
    val sueldo: Int? = null,
    var errormessaage: String? = null,
    val tecnico: List<TecnicoEntity> = emptyList()
)