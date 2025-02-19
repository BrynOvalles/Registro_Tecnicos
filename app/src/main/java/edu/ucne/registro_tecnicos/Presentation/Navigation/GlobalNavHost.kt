package edu.ucne.registro_tecnicos.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registro_tecnicos.Presentation.Login.LoginScreen
import edu.ucne.registro_tecnicos.Presentation.Login.MensajeScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TecnicosListScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TecnicosScreen
import edu.ucne.registro_tecnicos.Presentation.Ticket.TicketsListScreen
import edu.ucne.registro_tecnicos.Presentation.Ticket.TicketsScreen
import edu.ucne.registrotecnicos.presentation.prioridades.PrioridadListScreen
import edu.ucne.registrotecnicos.presentation.prioridades.PrioridadScreen

@Composable
fun GlobalNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ){
        composable<Screen.TecnicosList> {
            TecnicosListScreen(
                createTecnicos = {
                    navHostController.navigate(Screen.Tecnicos(0))
                },
                goTecnicos = {tecnicoId ->
                    navHostController.navigate(Screen.Tecnicos(tecnicoId= tecnicoId))
                }

            )
        }
        composable<Screen.Tecnicos> {arg->
            val tecnicoId = arg.toRoute<Screen.Tecnicos>().tecnicoId
            TecnicosScreen(
                tecnicoId = tecnicoId,
                goTecnicosList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.TicketsList> {
            TicketsListScreen(
                createTickets = {
                    navHostController.navigate(Screen.Tickets(0))
                },
                goTickets = {
                    navHostController.navigate(Screen.Tickets(it))
                }
            )
        }
        composable<Screen.Tickets> {arg->
            val ticketId = arg.toRoute<Screen.Tickets>().ticketId
            TicketsScreen(
                goTicketsList = {
                    navHostController.navigateUp()
                },
                ticketId = ticketId,
                createMensajes = {
                    navHostController.navigate(Screen.Mensajes(0,ticketId))
                }
            )
    }
        composable<Screen.Login> {
            LoginScreen(
                goTecnicosList = {
                    navHostController.navigate(Screen.TecnicosList)
                },
                goTicketsList = {
                    navHostController.navigate(Screen.TicketsList)
                },
                goPrioridadList = {
                    navHostController.navigate(Screen.PrioridadList)
                }
            )
        }

        composable<Screen.Mensajes> {
            MensajeScreen(
                createMensajes = {
                    navHostController.navigate(Screen.Mensajes(0,0))
                },
                goTicket = {
                    navHostController.navigateUp()
                },
                tecnicoId = it.toRoute<Screen.Mensajes>().tecnicoId,
                ticketId = it.toRoute<Screen.Mensajes>().ticketId
            )
        }

        composable<Screen.PrioridadList> {
            PrioridadListScreen(
                createPrioridad = { navHostController.navigate(Screen.Prioridad(0)) },
                goToPrioridad = { navHostController.navigate(Screen.Prioridad(it)) }
            )
        }
        composable<Screen.Prioridad> {
            val prioridadId = it.toRoute<Screen.Prioridad>().prioridadId
            PrioridadScreen(
                prioridadId = prioridadId,
                goPrioridadList = { navHostController.navigateUp() }
            )
        }
}
}