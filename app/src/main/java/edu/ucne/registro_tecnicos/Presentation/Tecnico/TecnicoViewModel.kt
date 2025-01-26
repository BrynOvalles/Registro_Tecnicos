package edu.ucne.registro_tecnicos.Presentation.Tecnico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TecnicoViewModel @Inject constructor(
    private val tecnicoRepository: TecnicoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TecnicoUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getTecnico()
    }

    fun save(): Boolean{
        var isSaved = false
        if (uiState.value.nombre.isNotBlank() || uiState.value.sueldo != null) {
            viewModelScope.launch {
                tecnicoRepository.saveTecnico(uiState.value.toEntity())
            isSaved = true
            }
        }
        else {
            _uiState.update {
                it.copy(errormessaage = "Debes Llenar Todos Los Campos")
            }
        }
        return isSaved
    }

    fun onNombreChange(nombre: String) {
        _uiState.update {
            it.copy(
                nombre = nombre,
                errormessaage = if (nombre.isBlank()) "El Nombre No Puede Esta Vacio"
                else null
            )
        }
    }

    fun onSueldoChange(sueldo: Int) {
        _uiState.update {
            it.copy(
                sueldo = sueldo,
                errormessaage = if ( sueldo < 0) "El Sueldo Debe Llenarse Con Numeros"
                else null
            )
        }
    }

    fun delete(){
        viewModelScope.launch {
            tecnicoRepository.delete(uiState.value.toEntity())
        }
    }

    private fun getTecnico(){
        viewModelScope.launch {
            tecnicoRepository.getAll().collect { tecnico->
                _uiState.update {
                    it.copy(tecnico = tecnico)
                }
            }
        }
    }

    fun find(tecnicoId: Int) {
        viewModelScope.launch {
            if (tecnicoId > 0) {
                val tecnico = tecnicoRepository.find(tecnicoId)
                if (tecnico != null) {
                    _uiState.update {
                        it.copy(
                            tecnicoId = tecnico.tecnicosId,
                            nombre = tecnico.nombre,
                            sueldo = tecnico.sueldo.toInt()
                        )
                    }
                }
            }
        }
    }

    fun new(){
        _uiState.value = TecnicoUiState()
    }
}

fun TecnicoUiState.toEntity() = TecnicoEntity(
    tecnicosId = this.tecnicoId,
    nombre = this.nombre,
    sueldo = this.sueldo.toString()
)