package com.example.padm.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.padm.net.joinGame
import com.example.padm.ui.components.RopeDivider
import com.example.padm.ui.theme.PirateColors
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
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(PirateColors.SeaNight, PirateColors.SeaDeep)))
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(28.dp), clip = false)
                .clip(RoundedCornerShape(28.dp))
                .background(Brush.verticalGradient(listOf(PirateColors.ParchmentLight, PirateColors.ParchmentDark)))
                .border(2.dp, PirateColors.Leather, RoundedCornerShape(28.dp))
                .padding(22.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "🏴‍☠️  Embarquez, moussaillon !",
                    fontSize = 20.sp,
                    color = Color(0xFF2C1608),
                    fontFamily = FontFamily.Serif
                )

                RopeDivider()

                OutlinedTextField(
                    value = gameId,
                    onValueChange = { gameId = it.uppercase().trimStart() },
                    label = { Text("ID de la partie (Game ID)") },
                    leadingIcon = { Text("🗺️") },
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
                    label = { Text("Nom de pirate (Player ID)") },
                    leadingIcon = { Text("🦜") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                val buttonShape = RoundedCornerShape(16.dp)
                val pirateGradient = Brush.horizontalGradient(listOf(PirateColors.Leather, PirateColors.Copper))

                Button(
                    onClick = {
                        status = null
                        isError = false
                        loading = true
                        scope.launch {
                            val result = joinGame(gameId.trim(), playerId.trim())
                            loading = false
                            if (result.ok) {
                                status = "✅ ${result.message} (numéro=${result.playerNumber ?: "?"})"
                                onJoined(gameId.trim(), playerId.trim(), result.playerNumber)
                            } else {
                                status = "❌ ${result.message}"
                                isError = true
                            }
                        }
                    },
                    enabled = isValid && !loading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(alpha = 0.6f)
                    ),
                    contentPadding = PaddingValues(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .clip(buttonShape)
                        .background(pirateGradient, shape = buttonShape)
                        .border(1.dp, Color(0xFF4D2F14), buttonShape)
                ) {
                    Text(if (loading) "En cours..." else "Embarquer ⚓", fontSize = 18.sp)
                }

                status?.let {
                    val color = if (isError) MaterialTheme.colorScheme.error else Color(0xFF1B5E20)
                    Text(text = it, color = color, fontFamily = FontFamily.Serif)
                }
            }
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp).align(Alignment.BottomEnd).padding(8.dp),
                color = PirateColors.Copper
            )
        }
    }
}