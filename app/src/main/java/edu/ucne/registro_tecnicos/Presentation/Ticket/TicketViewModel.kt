package edu.ucne.registro_tecnicos.Presentation.Ticket

import android.text.BoringLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.registro_tecnicos.data.local.entity.TecnicoEntity
import edu.ucne.registro_tecnicos.data.local.entity.TicketEntity
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import edu.ucne.registro_tecnicos.data.repository.TicketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class TicketViewModel @Inject constructor(
    private val ticketRepository: TicketRepository,
    private val tecnicoRepository: TecnicoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    private val _tecnicosList = MutableStateFlow<List<TecnicoEntity>>(emptyList())
    val tecnicosList = _tecnicosList.asStateFlow()

    init {
        getTickets()
        getTecnicos()
    }

    fun save(): Boolean{
        var isSave = false
        viewModelScope.launch {
            val state = uiState.value
            if (
                state.asunto.isNotBlank() ||
                state.cliente.isNotBlank() ||
                state.descripcion.isNotBlank() ||
                state.tecnicoId != null ||
                state.prioridadId != null ||
                state.fecha != null
                ){
                viewModelScope.launch {
                ticketRepository.save(state.toEntity())
                isSave = true
                }
            } else {
                _uiState.update {
                    it.copy(errorMessage = "Debe completar todos los campos")
                }
            }
        }
        return isSave
    }
    fun onPrioridadChange(prioridadId: Int){
        _uiState.update {
            it.copy(
                prioridadId = prioridadId,
                errorMessage = if (prioridadId == 0) "Selecciona Un Tecnico"
                else null
            )
        }
    }
    fun onAsuntoChange(asunto:String){
        _uiState.update {
            it.copy(
                asunto=asunto,
                errorMessage = if(asunto.isBlank()) "Se Requiere Un Asunto"
                else null
            )
        }

    }
    fun onDescripcionChange(descripcion:String){
        _uiState.update {
            it.copy(
                descripcion= descripcion,
                errorMessage = if(descripcion.isBlank()) "Se Requiere Una Descripcion"
                else null
            )
        }
    }
    fun onFechaChange(fecha: String){
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        _uiState.update {
            it.copy(
                fecha = dateFormatter.parse(fecha),
                errorMessage = if(fecha.isBlank())"Debes Seleccionar Una Fecha"
                else null
            )
        }
    }
    fun onTecnicoChange(tecnicoId: Int){
        _uiState.update {
            it.copy(
                tecnicoId=tecnicoId,
                errorMessage = if (tecnicoId == 0) "Selecciona Un Tecnico"
                else null
            )
        }
    }
    fun onClienteChange(cliente:String){
        _uiState.update {
            it.copy(
                cliente=cliente,
                errorMessage = if (cliente.isBlank()) "Debe Proporcionar El Nombre Del Cliente"
                else null
            )
        }
    }
    fun new(){
        _uiState.value = TicketUiState()
    }
    fun delete(){
        viewModelScope.launch {
            ticketRepository.delete(uiState.value.toEntity())
        }
    }
    private fun getTickets(){
        viewModelScope.launch {
            ticketRepository.getAll().collect{ tickets->
                _uiState.update {
                    it.copy(tickets = tickets)
                }
            }
        }
    }
    private fun getTecnicos(){
        viewModelScope.launch {
            tecnicoRepository.getAll().collect{tecnicos->
                _tecnicosList.value = tecnicos

            }
        }
    }
    fun find(ticketId: Int) {
        viewModelScope.launch {
            if (ticketId > 0) {
                val ticket = ticketRepository.find(ticketId)
                if (ticket != null) {
                    _uiState.update {
                        it.copy(
                            tecnicoId = ticket.tecnicoId,
                            fecha = ticket.fecha,
                            cliente = ticket.cliente,
                            asunto = ticket.asunto,
                            descripcion = ticket.descripcion,
                            prioridadId = ticket.prioridadId,
                            ticketId = ticket.ticketId
                        )
                    }
                }
            }
        }
    }
}

fun TicketUiState.toEntity() = TicketEntity(
    ticketId = this.ticketId,
    fecha = this.fecha,
    prioridadId = this.prioridadId,
    cliente = this.cliente,
    asunto = this.asunto,
    descripcion = this.descripcion,
    tecnicoId = this.tecnicoId
)