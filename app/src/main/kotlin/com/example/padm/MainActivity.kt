package com.example.padm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.padm.screens.JoinScreen
import com.example.padm.screens.LobbyScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PADMApp()
        }
    }
}

@Composable
fun PADMApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "join",
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            // JoinScreen
            composable("join") {
                JoinScreen(onJoined = { gameId, playerId, playerNumber ->
                    navController.navigate("lobby/$gameId/$playerId/${playerNumber ?: -1}")
                })
            }

            // LobbyScreen
            composable(
                route = "lobby/{gameId}/{playerId}/{playerNumber}",
                arguments = listOf(
                    navArgument("gameId") { type = NavType.StringType },
                    navArgument("playerId") { type = NavType.StringType },
                    navArgument("playerNumber") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val gameId = backStackEntry.arguments?.getString("gameId").orEmpty()
                val playerId = backStackEntry.arguments?.getString("playerId").orEmpty()
                val playerNumber = backStackEntry.arguments?.getInt("playerNumber") ?: -1
                LobbyScreen(gameId, playerId, playerNumber)
            }
        }
    }
}
