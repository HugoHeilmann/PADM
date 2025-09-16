package com.example.padm.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.padm.net.joinGame
import kotlinx.coroutines.launch

@Composable
fun JoinScreen(
    modifier: Modifier = Modifier,
    onJoined: (gameId: String, playerId: String, playerNumber: Int?) -> Unit = { _, _, _ -> }
) {
    var gameId by remember { mutableStateOf("") }
    var playerId by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf<String?>(null) }
    var isError by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    val isValid = gameId.trim().isNotEmpty() && playerId.trim().isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Rejoindre une partie",
                fontSize = 22.sp
            )

            OutlinedTextField(
                value = gameId,
                onValueChange = { input ->
                    gameId = input.uppercase().trimStart()
                },
                label = { Text("Game ID") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Characters,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = playerId,
                onValueChange = { playerId = it.trimStart() },
                label = { Text("Player ID (pseudo)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    status = null
                    isError = false
                    loading = true
                    scope.launch {
                        val result = joinGame(gameId.trim(), playerId.trim())
                        loading = false

                        if (result.ok) {
                            status = "✅ ${result.message} (playerNumber=${result.playerNumber ?: "?"})"
                            isError = false
                            onJoined(gameId.trim(), playerId.trim(), result.playerNumber)
                        } else {
                            status = "❌ ${result.message}"
                            isError = true
                        }
                    }
                },
                enabled = isValid && !loading,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (loading) "Connexion..." else "Rejoindre")
            }

            if (status != null) {
                val color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                Text(text = status!!, color = color)
            }
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }
    }
}