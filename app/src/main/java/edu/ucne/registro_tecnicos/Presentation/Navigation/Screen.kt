package edu.ucne.registro_tecnicos.Presentation.Navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object TecnicosList : Screen()
    @Serializable
    data class Tecnicos(val tecnicoId: Int) : Screen()
    @Serializable
    data object TicketsList : Screen()
    @Serializable
    data class Tickets(val ticketId: Int) : Screen()
    @Serializable
    data object Login : Screen()
}