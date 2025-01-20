package edu.ucne.registro_tecnicos.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registro_tecnicos.Presentation.Tecnico.LoginScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TecnicosListScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TecnicosScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TicketsListScreen
import edu.ucne.registro_tecnicos.Presentation.Tecnico.TicketsScreen
import edu.ucne.registro_tecnicos.data.repository.TecnicoRepository
import edu.ucne.registro_tecnicos.data.repository.TicketRepository

@Composable
fun TecnicosNavHost(
    tecnicoRepository: TecnicoRepository,
    ticketsRepository: TicketRepository,
    navHostController: NavHostController
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val tecnicosList by tecnicoRepository.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    val ticketsList by ticketsRepository.getAll()
        .collectAsStateWithLifecycle(
            initialValue = emptyList(),
            lifecycleOwner = lifecycleOwner,
            minActiveState = Lifecycle.State.STARTED
        )
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login
    ){
        composable<Screen.TecnicosList> {
            TecnicosListScreen(
                tecnicosList = tecnicosList,
                createTecnicos = {
                    navHostController.navigate(Screen.Tecnicos(0))

                }
            )
        }
        composable<Screen.Tecnicos> {arg->
            val id = arg.toRoute<Screen.Tecnicos>().tecnicoId
            TecnicosScreen(
                tecnicoRepository = tecnicoRepository,
                goTecnicosList = {
                    navHostController.navigateUp()
                }
            )
        }
        composable<Screen.TicketsList> {
            TicketsListScreen(
                ticketsList = ticketsList,
                createTickets = {
                    navHostController.navigate(Screen.Tickets(0))

                },
                goTickets = {
                    navHostController.navigate(Screen.Tickets(it))
                }
            )
        }
        composable<Screen.Tickets> {arg->
            val id = arg.toRoute<Screen.Tickets>().ticketId
            TicketsScreen(
                ticketsRepository = ticketsRepository,
                goTicketsList = {
                    navHostController.navigateUp()
                },
                tecnicosList = tecnicosList,
                ticketId = id
            )
    }
        composable<Screen.Login> {
            LoginScreen(
                goTecnicosList = {
                    navHostController.navigate(Screen.TecnicosList)
                },
                goTicketsList = {
                    navHostController.navigate(Screen.TicketsList)
                }
            )
        }
}
}