package com.example.padm.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.padm.ui.components.RopeDivider
import com.example.padm.ui.theme.PirateColors

@Composable
fun LobbyScreen(
    gameId: String,
    playerId: String,
    playerNumber: Int
) {
    // Fond marin
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(PirateColors.SeaNight, PirateColors.SeaDeep)
                )
            )
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        // Parchemin central
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(12.dp, RoundedCornerShape(28.dp), clip = false)
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.verticalGradient(
                        listOf(PirateColors.ParchmentLight, PirateColors.ParchmentDark)
                    )
                )
                .border(2.dp, PirateColors.Leather, RoundedCornerShape(28.dp))
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "⚓ Salle d’équipage ⚓",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color(0xFF2C1608),
                    fontFamily = FontFamily.Serif,
                )

                RopeDivider()

                Text(
                    text = "🗺️ Partie : $gameId",
                    fontSize = 18.sp,
                    color = PirateColors.Leather,
                    fontFamily = FontFamily.Serif
                )

                Text(
                    text = "🦜 Moussaillon : $playerId (#$playerNumber)",
                    fontSize = 18.sp,
                    color = PirateColors.Leather,
                    fontFamily = FontFamily.Serif
                )

                RopeDivider()

                Text(
                    text = "En attente d’autres flibustiers…",
                    fontSize = 16.sp,
                    color = PirateColors.Copper,
                    fontFamily = FontFamily.Serif
                )
            }
        }
    }
}
