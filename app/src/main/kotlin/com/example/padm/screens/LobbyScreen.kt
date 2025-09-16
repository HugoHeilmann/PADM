package com.example.padm.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LobbyScreen(
    gameId: String,
    playerId: String,
    playerNumber: Int
) {
    Box(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("Lobby\nGame: $gameId\nPlayer: $playerId (#$playerNumber)")
    }
}